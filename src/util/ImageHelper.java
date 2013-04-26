package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Class with static methods that helps handling 
 * and resizing images
 * @author Henrique Moraes
 *
 */
public class ImageHelper {
    
    /**
     * Resizes the image to to square size from a given path
     * @param path path of the image to scale
     * @param size The height and width of the scaled image
     * @return square Imaged scaled to the user parameter
     */
    public static Image getScaledImage(String path, int size){
        return getScaledImage(getImageIcon(path).getImage(),size);
    }
    
    /**
     * Resizes image from a given path
     * @param path path of the image to scale
     * @param width The new width of the scaled image
     * @param height The new height of the scaled image
     * @return Image scaled to the user parameter
     */
    public static Image getScaledImage(String path, int width, int height){
        return getScaledImage(getImageIcon(path).getImage(),width, height);
    }
    
    /**
     * Gets an image icon from the given path
     * @param path path of the image
     * @return The image icon for this image
     */
    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(ImageHelper.class.getResource(path));
    }
    
    /**
     * Gets an image from the given path
     * @param path path of the image
     * @return The image associated to this path
     */
    public static Image getImage(String path) {
        return new ImageIcon(ImageHelper.class.getResource(path)).getImage();
    }
         
    /**
     * Resizes an Image into square dimensions
     * @param image the image to scale
     * @param size The height and width of the scaled image
     * @return Image scale to the user parameter
     */
    public static Image getScaledImage(Image image,int size){
        return getScaledImage(image, size, size);
    }
    
    /**
     * Resizes an Image into square dimensions from a given icon
     * @param icon The ImageIcon to have its image scaled
     * @param size The height and width of the scaled image
     * @return Image scaled to the user parameter
     */
    public static Image getScaledImage(ImageIcon icon,int size){
        return getScaledImage(icon.getImage(), size, size);
    }
    
    /**
     * Resizes an Image into square dimensions from a given icon
     * @param icon the icon to have its image scaled
     * @param size The height and width of the scaled image
     * @return Image scaled to the user parameter
     */
    public static Image getScaledImage(ImageIcon icon,int width, int height){
        return getScaledImage(icon.getImage(), width, height);
    }
    
    /**
     * Resizes an Image
     * @param image the image to scale
     * @param width The new width of the scaled image
     * @param height The new height of the scaled image
     * @return Image scaled to the user parameter
     */
    public static Image getScaledImage(Image image,int width, int height){
        BufferedImage buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return buffer;
    }
}
