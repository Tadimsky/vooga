
package vooga.scroller.level_editor;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import vooga.scroller.viewUtil.IView;
import vooga.scroller.viewUtil.RadioGroup;
import vooga.scroller.viewUtil.Renderable;
import vooga.scroller.viewUtil.WindowComponent;
/**
 * EditorView is responsible for presenting editing tools to the user.
 * It provides a UI for the user to select various kind of sprites or 
 * environment elements and add them to the actual level being edited.
 * @author Dagbedji Fagnisse
 *
 */
public class LEToolsView extends WindowComponent {

    private LETools myTools;
    private RadioGroup spriteUI;

    public LEToolsView (IView parent, double d, double e) {
        super(parent, d, e);
        // TODO Auto-generated constructor stub
    }
    
    public LEToolsView (LEWorkspaceView parent, double d, double e) {
        super(parent, d, e);
        myTools = (LETools) parent.getTools();
        spriteUI = new RadioGroup(new CreateSpriteListener(),
                                        myTools.getSpriteMakingOptions());
        this.add(spriteUI);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initializeVariables () {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void addComponents () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render (Renderable r) {
        // TODO Auto-generated method stub
        
    }
    
    private class CreateSpriteListener implements ActionListener {

        @Override
        public void actionPerformed (ActionEvent e) {
            createSprite(e.getActionCommand());
            spriteUI.updateState();
        }
        
    }

    public void createSprite (String spriteID) {
        // TODO Auto-generated method stub
        String res = "cs " + spriteID;
        System.out.println(res);
        processCommand(res);
    }
    
    public void setTools(LETools t) {
        myTools = t;
    }

}
