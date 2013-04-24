package vooga.rts.networking.examplechat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 * Creates a panel that communicates with an IChatModel.
 * 
 * @author David Winegar
 * 
 */
public class ChatPanel extends JPanel {

    private static final String EMPTY_STRING = "";
    private static final long serialVersionUID = -9126798689207961507L;
    private JTextArea myTextArea;
    private JTextField myInputArea;
    private IChatModel myModel;

    /**
     * Sets the model and instanitates state.
     * 
     * @param model to set
     */
    public ChatPanel (IChatModel model) {
        myModel = model;
        setLayout(new BorderLayout(0, 0));
        createChatPane();
        createOutputPane();
    }

    /**
     * Writes a message to the text area.
     * 
     * @param message to write
     */
    public void appendMessage (String message) {
        myTextArea.append(message);
    }

    private void createOutputPane () {
        myTextArea = new JTextArea();
        myTextArea.setEditable(false);
        JScrollPane pane = new JScrollPane(myTextArea);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane, BorderLayout.CENTER);
    }

    private void createChatPane () {
        myInputArea = new JTextField();
        myInputArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myModel.messageEntered(myInputArea.getText());
                myInputArea.setText(EMPTY_STRING);
            }
        });
        add(myInputArea, BorderLayout.SOUTH);
    }

}
