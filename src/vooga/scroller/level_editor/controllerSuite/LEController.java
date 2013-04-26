package vooga.scroller.level_editor.controllerSuite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import vooga.scroller.level_editor.ILevelEditor;
import vooga.scroller.level_editor.library.IBackgroundLibrary;
import vooga.scroller.level_editor.library.ISpriteLibrary;
import vooga.scroller.level_editor.model.LevelEditor;
import vooga.scroller.level_editor.model.LevelParser;
import vooga.scroller.level_editor.model.LevelWriter;
import vooga.scroller.level_editor.view.LEGridView;
import vooga.scroller.level_editor.view.LEView;
import vooga.scroller.level_editor.view.LEWorkspaceView;
import vooga.scroller.level_editor.view.LevelEditing;
import vooga.scroller.util.Editable;
import vooga.scroller.util.Renderable;
import vooga.scroller.util.mvc.IController;
import vooga.scroller.util.mvc.IWindow;
import vooga.scroller.util.mvc.vcFramework.WorkspaceView;


/**
 * The controller is responsible for interfacing between an IView and an IModel.
 * Among other things, it is responsible for <LI>Instantiating a generic model and a view</LI> <LI>
 * Keeping track of multiple high-level domain-specific objects (eg. Room, Level...)</LI> <LI>Send
 * Renderable versions to the adequate IView workspace</LI> <LI>Send an "Editable" versions to the
 * Model</LI> <LI>Ensuring that all high-level domain instances are kept in sync.
 * 
 * @author SLogo team 3, Dagbedji F.
 * 
 */

public class LEController implements IController<LevelEditing> {

    private static final String SAVE_ERROR = "All levels need a StartPoint, Portal, and Background";

    public static void runLevelEditor (ISpriteLibrary lib, IBackgroundLibrary bgLib) {
        LEController con = new LEController(lib, bgLib);
        con.start();
    }

    private static final int DEFAULT_SPRITE_GRID_SIZE = 30;
    private LevelEditing myDomainInfo;
    private LevelParser myLevelReader;
    private LevelWriter myLevelWriter;
    private ILevelEditor myModel;
    private Map<WorkspaceView<LevelEditing>, Editable> myTab2Workspace;
    private LETools myTools;
    private ToolsManager myToolsManager;
    private IWindow<LEWorkspaceView, LevelEditing, LEGridView, LETools> myView;
    private Map<Editable, WorkspaceView<LevelEditing>> myWorkspace2Tab;

    /**
     * Preferred constructor, specifies sprites and background to be availed in the
     * Level Editor.
     * 
     * @param lib - A sprite Library for the editor
     * @param bgLib - A background Library to be used in the editor
     */
    public LEController (ISpriteLibrary lib, IBackgroundLibrary bgLib) {
        myDomainInfo = new LevelEditing();
        initLevelEditor();
        myToolsManager = new ToolsManager(lib, bgLib);
        myModel.setSpriteMap(myToolsManager.getSpriteMap());
        myModel.setBackgroundMap(bgLib.getBackgrounds());
        myTools = myToolsManager.getViewTools();
        myView.setDefaultWorkspaceTools(myTools);
        myWorkspace2Tab = new HashMap<Editable, WorkspaceView<LevelEditing>>();
        myTab2Workspace = new HashMap<WorkspaceView<LevelEditing>, Editable>();
        myLevelWriter = new LevelWriter();
        myLevelReader = new LevelParser();
    }

    /**
     * @param id
     * @param m
     */
    private void createWorkspaceView (int id, LEGrid m) {
        LEWorkspaceView associatedWorkspaceView =
                myView.initializeWorkspaceView(id, (Renderable<LEGridView>) m);
        myWorkspace2Tab.put(m, associatedWorkspaceView);
        myTab2Workspace.put(associatedWorkspaceView, m);
        myView.showWorkspace(associatedWorkspaceView, (Renderable<LEGridView>) m);
    }

    @Override
    public LevelEditing getDomainInfo () {
        return myDomainInfo;
    }

    private void initLevelEditor () {
        String language = getLanguage();
        myModel = new LevelEditor();
        myView = new LEView(language, this);
    }

    private String getLanguage () {
        String[] languages = { "English", "French" };
        int n =
                JOptionPane.showOptionDialog(null,
                                             "Choose a language", "Language Selection",
                                             JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE,
                                             null, languages, languages[0]);
        String language = languages[n];
        return language;
    }

    /**
     * return the Room Object corresponding to the input TabView
     * 
     * @param t
     * @return
     */
    private Editable getModelForWorkspace (WorkspaceView<LevelEditing> v) {
        return myTab2Workspace.get(v);
    }

    /**
     * This allows the user to specify the number of blocks needed for the level.
     * 
     * @return [width, height] in blocks
     */
    private int[] getNumBlocks () {
        int[] res = new int[2];
        String s =
                (String) JOptionPane.showInputDialog(
                                                     null,
                                                     "How many blocks:\n"
                                                             + "width, height",
                                                     "New Level Size",
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     null,
                                                     "" + DEFAULT_SPRITE_GRID_SIZE + ", " +
                                                             DEFAULT_SPRITE_GRID_SIZE);
        if (!s.isEmpty()) {
            int splitter = s.indexOf(",");
            String s1 = s.substring(0, splitter).trim();
            String s2 = s.substring(splitter + 1).trim();
            res[0] = Integer.parseInt(s1);
            res[1] = Integer.parseInt(s2);
        }
        else {
            res[0] = DEFAULT_SPRITE_GRID_SIZE;
            res[1] = DEFAULT_SPRITE_GRID_SIZE;
        }
        return res;
    }

    @Override
    public void initializeWorkspace () {
        int id = myWorkspace2Tab.size();
        int[] size = getNumBlocks();
        initializeWorkspace(id, size[0], size[1]);
    }

    /**
     * Initialize an LE workspace
     * 
     * @param numWidthBlocks - blocks per width (row)
     * @param numHeightBlocks - block per height (columns)
     */
    public void initializeWorkspace (int numWidthBlocks, int numHeightBlocks) {
        int id = myWorkspace2Tab.size();
        initializeWorkspace(id, numWidthBlocks, numHeightBlocks);
    }

    private void initializeWorkspace (int id, int numWidthBlocks, int numHeightBlocks) {
        LEGrid m = new LEGrid(numWidthBlocks, numHeightBlocks);
        createWorkspaceView(id, m);
    }

    @Override
    public void loadFile (File file2open) {
        LEGrid m = myLevelReader.makeGridFromFile(file2open);
        int id = myWorkspace2Tab.size();
        createWorkspaceView(id, m);
    }

    @Override
    public void process (WorkspaceView<LevelEditing> t, Object cmd) {
        LEGrid m = (LEGrid) getModelForWorkspace(t);
        System.out.println("Controller got " + cmd);
        if (cmd instanceof String) {
            myModel.processCommand(m, (String) cmd);
        }
        t.setRenderable((Renderable<?>) m);
    }

    @Override
    public void saveFile (File file2save, WorkspaceView<LevelEditing> t) {
        LEGrid grid = (LEGrid) getModelForWorkspace(t);
        if (checkSaveable(grid)) {
            myLevelWriter.createFile(file2save, grid, myToolsManager.getSpriteLibPath());
            grid.saveThumbnail(file2save.getPath());
        }
        else {
            showErrorMsg(SAVE_ERROR);
        }

    }

    private boolean checkSaveable (LEGrid grid) {
        return grid.isValidForSave();
    }

    @Override
    public void start () {
        // Welcome message
        myView.start();
    }

    public static void showErrorMsg (String copyError) {
        // TODO Auto-generated method stub

    }

}
