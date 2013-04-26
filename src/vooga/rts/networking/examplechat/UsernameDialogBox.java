package vooga.rts.networking.examplechat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Creates the dialog box allowing users to enter a user name.
 * 
 * @author David Winegar
 * 
 */
public class UsernameDialogBox {

    private static final int DEFAULT_HEIGHT = 80;
    private static final int DEFAULT_WIDTH = 200;
    private JDialog myDialog;
    private JTextField myField;
    private IChatModel myExampleChat;

    /**
     * 
     * @param chat example chat
     */
    public UsernameDialogBox (IChatModel chat) {
        myExampleChat = chat;
        myDialog = new JDialog();
        myDialog.setLayout(new BorderLayout());
        myDialog.setVisible(true);
        myField = new JTextField();
        myDialog.add(new JLabel("Enter Name: "), BorderLayout.NORTH);
        myField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                myDialog.dispose();
                myExampleChat.switchToServerBrowser(myField.getText());
            }
        });
        myDialog.add(myField, BorderLayout.CENTER);
        myDialog.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        myDialog.pack();
    }
}
