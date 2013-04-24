package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * A Panel containing a background image.  Other content can be added onto this
 * panel, with optional margins.
 * 
 * 
 * @author Henrique Moraes, Ellango Jothimurugesan
 *
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel{
    
    private Image myBackgroundImage;
    
   /**
    * Constructs the background panel with the filepath for the image, and 
    * zero margins.
    * 
    * The filepath should be with respect to the src folder.
    * 
    * @param filepath for the image to set as the background
    */
    public BackgroundPanel(String filepath) {
        this(filepath, 0, 0);   
    }
    
    /**
     * Constructs the background panel with the filepath for the image, and the
     * provided margins.
     * 
     * The filepath should be with respect to the src folder.
     * 
     * @param filepath for the image to set as the background
     * @param hgap is the space on the left/right sides that will not be 
     * filled by added content.
     * @param vgap is the space on the top/bottom sides that will not be 
     * filled by added content.
     */
    public BackgroundPanel(String filepath, int hgap, int vgap) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(filepath));
        setBackgroundImage(icon);
        defineMargin(hgap, vgap);
        repaint();  
    }
    
    /**
     * Makes the provided component transparent, and then adds it to this panel.
     * 
     * @param component to be added
     */
    public void add(JComponent component) {
        component.setOpaque(false);
        super.add(component);
    }
    
    /**
     * Sets the background image using the provided image icon.
     * 
     * 
     * @param img ImageIcon to set on the background
     */
    public void setBackgroundImage(ImageIcon img){
        setBackgroundImage(img.getImage());
    }
    
    /**
     * Sets the background image using the provided image.
     * 
     * @param img Image to set on the background
     */
    public void setBackgroundImage(Image img){
        myBackgroundImage = img;
        repaint();
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
        pen.drawImage(myBackgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    
}
