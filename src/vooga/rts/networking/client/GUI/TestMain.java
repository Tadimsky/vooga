package vooga.rts.networking.client.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Test Main for GUI package.
 * @author David Winegar
 *
 */
public class TestMain {

    public static void main (String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        ViewContainerPanel panel = new ViewContainerPanel("test");
        frame.add(panel);
        //panel.changeView(new ServerBrowserView(new ServerBrowserTableAdapter()), " server browser");
        panel.changeView(new CreateLobbyView(), " lobby view");
        frame.setVisible(true);
        frame.pack();
    }
}
