package vooga.towerdefense.view.introscreens;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class SplashScreen extends JPanel {

    private static final String RESOURCE = "/vooga/towerdefense/images/";
    private static final long serialVersionUID = 1L;
    private java.awt.Image myBackgroundImage;

    public SplashScreen (Dimension size) {
        setPreferredSize(size);
        myBackgroundImage =
                new ImageIcon(getClass().getResource(RESOURCE + "splashscreen.gif")).getImage();
    }

    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        if (myBackgroundImage != null) {
            pen.drawImage(myBackgroundImage, 0, 0, this.getSize().width, this.getSize().height,
                          null);
        }

    }

}
