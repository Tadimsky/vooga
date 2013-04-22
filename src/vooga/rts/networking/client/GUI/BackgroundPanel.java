package vooga.rts.networking.client.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel containing a background image
 * @author Henrique Moraes, Ellango Jothimurugesan
 *
 */
public class BackgroundPanel extends JPanel{
    Image myImage;
    JPanel myInnerPanel;
    private static final int H_GAP = 120;
    private static final int V_GAP = 50;
    private static final String IMAGE_PATH = "../../resources/Scroll1.jpg";
    private String myPath;
    
   /**
    * Constructor
    * @param path image path for the image to set on the background
    */
    public BackgroundPanel(String path) {
        myPath = path;
        ImageIcon icon = new ImageIcon(this.getClass().getResource(myPath));
        setBackgroundImage(icon);
        setLayout(new BorderLayout(0, 0));
        defineMargin(H_GAP,V_GAP);
        repaint();            
    }
    
    /**
     * Sets the background of this panel with a default image
     */
    public BackgroundPanel() {
        this(IMAGE_PATH);                 
    }
    
    /**
     * 
     * @param img Image to set on the background
     */
    public void setBackgroundImage(Image img){
        myImage = img;
        repaint();
    }
    
    /**
     * 
     * @param img Image Icon to set on the background
     */
    public void setBackgroundImage(ImageIcon img){
        setBackgroundImage(img.getImage());
    }
    
    /**
     * Defines the margin spacing between this panel and the component inside
     * it
     * @param hgap horizontal gap
     * @param vgap vertical gap
     */
    public void defineMargin(int hgap, int vgap) {
        setBorder(new EmptyBorder(vgap, hgap, vgap, hgap));
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D pen = (Graphics2D) g;
        pen.drawImage(myImage, 0, 0, getWidth(), getHeight(), this);
    }
    
}
