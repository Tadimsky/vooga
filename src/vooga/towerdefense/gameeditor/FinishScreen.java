package vooga.towerdefense.gameeditor;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


/**
 * temporary placeholder. Will edit soon!
 * 
 * @author Angelica Schwartz
 */
public class FinishScreen extends GameEditorScreen {

    /**
     * default serialized id.
     */
    private static final long serialVersionUID = 1L;
    private static final String FINISH_TEXT = "Finish & save file";
    private static final String TITLE_NAME = "FINISH";
    private static final String NEXT_SCREEN_NAME = null;
    private JButton myFinishButton;

    public FinishScreen (Dimension size,
                         GameEditorController controller) {
        super(size, controller, TITLE_NAME, NEXT_SCREEN_NAME);
        myFinishButton = new JButton(FINISH_TEXT);
        myFinishButton.addMouseListener(getMouseAdapter());
        add(myFinishButton);
    }

    @Override
    public void addElementToGame () {
        // TODO Auto-generated method stub

    }

    /**
     * adds additional behavior for a mouse click.
     * @param e is a MouseEvent
     */
    @Override
    public void addAdditionalMouseBehavior (MouseEvent e) {
        if (e.getSource().equals(myFinishButton)) {
            getController().saveFile();
        }
    }

}
