
package vooga.scroller.scrollingmanager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import util.Location;
import vooga.scroller.sprites.superclasses.Player;
/**
 * The default scrolling manager
 * @author Ross
 *
 */
public class OmniScrollingManager extends ScrollingManager {

    protected int upperpaintbound() {
        if (getModel() != null & getView() != null) {
            int vertical = (int) (((int) getModel().getUpperBoundary() 
                    + getView().getHeight() * levelLowerBoundary()) % getBackground().getHeight(null));
            return 0 - vertical;
        }
        return 0;
    }

    protected int lowerpaintbound() { 
        if (getModel() != null & getView() != null) {
            int vertical = (int) (((int) getModel().getLowerBoundary() 
                    + getView().getHeight() * levelLowerBoundary()) % getBackground().getHeight(null));
            return getView().getHeight() - vertical;
        }
        return 0;

    }

    protected int leftpaintbound() {
        if (getModel() != null && getView() != null) {
            int horizontal = (int) (((int) getModel().getLeftBoundary() 
                    + getView().getWidth() * levelRightBoundary()) % getBackground().getWidth(null));
            return 0 - horizontal;
        }
        return 0;
    }

    protected int rightpaintbound() {
        if (getModel() != null & getView() != null) {
            int horizontal = (int) (((int) getModel().getRightBoundary() 
                    + getView().getWidth() * levelRightBoundary()) % getBackground().getWidth(null));
            return getView().getWidth() - horizontal;
        }
        return 0;
    }
    
    /**
     * Paints the View for the game, given the constraints of the ScrollingManger.
     * @param pen The Graphics object that will be doing the painting.
     */  
    public void viewPaint(Graphics pen) {
        int leftpaintbound = leftpaintbound();
        int upperpaintbound = upperpaintbound();
        int rightpaintbound = rightpaintbound();
        int lowerpaintbound = lowerpaintbound();
        
        if (getModel().getLeftBoundary() < levelLeftBoundary()) {
            leftpaintbound = (int) levelLeftBoundary();
            rightpaintbound = leftpaintbound + getBackground().getWidth(null);
        }
        
        if (getModel().getRightBoundary() > levelRightBoundary()) {
            rightpaintbound = getView().getWidth()  - ((int) levelRightBoundary() 
                    % getModel().getBackground().getWidth(null));
            leftpaintbound = rightpaintbound - getBackground().getWidth(null);
            
        }
        if (getModel().getLowerBoundary() > levelLowerBoundary()) {
            lowerpaintbound = getView().getHeight()  - ((int) levelLowerBoundary() 
                    % getModel().getBackground().getHeight(null));
            upperpaintbound = lowerpaintbound - getBackground().getHeight(null);
        }
        if (getModel().getUpperBoundary() < levelUpperBoundary()) {
            upperpaintbound = (int) levelUpperBoundary();
            lowerpaintbound = upperpaintbound + getBackground().getHeight(null);
        }
        scrollerDrawImage(pen, leftpaintbound, upperpaintbound, rightpaintbound, lowerpaintbound);

    }
    

    private void scrollerDrawImage (Graphics pen, 
                                    int leftpaintbound, int upperpaintbound, 
                                    int rightpaintbound, int lowerpaintbound) {
        Image img = getBackground();
        int imgwidth = img.getWidth(null);
        int imgheight = img.getHeight(null);
        
//        System.out.println("Left : " + leftpaintbound);
//        System.out.println("Right : " + rightpaintbound);
//        System.out.println("Upper : " + upperpaintbound);
//        System.out.println("Lower : " + lowerpaintbound);
//        System.out.println("Width : " + imgwidth);
//        System.out.println("Height : " + imgheight);
        
        pen.drawImage(img, leftpaintbound, upperpaintbound, imgwidth, imgheight, null);
        pen.drawImage(img, rightpaintbound, upperpaintbound, imgwidth, imgheight, null);
        pen.drawImage(img, leftpaintbound, lowerpaintbound, imgwidth, imgheight, null);
        pen.drawImage(img, rightpaintbound, lowerpaintbound, imgwidth, imgheight, null);
        getModel().paint((Graphics2D) pen);
    }

    /**
     * Given a player, returns the Location given the current state of the Model 
     * that the Player needs to be painted. 
     * @param p The Player for which the paint Location is being updated.
     * @return The Location that the player should be painted.
     */  
    public Location playerPaintLocation (Player p) {
        double halfwidth = getView().getWidth() / 2;
        double halfheight = getView().getHeight() / 2;
        double x = halfwidth;
        double y = halfheight;
        double playerlocx = p.getX();
        double playerlocy = p.getY();
        if (playerlocx > (levelRightBoundary() - halfwidth)) {
            x =  halfwidth + (halfwidth - (levelRightBoundary() - playerlocx));
        }
        if (playerlocx < (levelLeftBoundary() + halfwidth)) {
            x =  halfwidth - (halfwidth - (levelLeftBoundary() + playerlocx));
        }
        if (playerlocy > (levelLowerBoundary() - halfheight)) {
            y =  halfheight + (halfheight - (levelLowerBoundary() - playerlocy));
        }
        if (playerlocy < (levelUpperBoundary() + halfheight)) {
            y =  halfheight - (halfheight - (levelUpperBoundary() + playerlocy));
        }        
        return new Location(x, y);
        
    }
}
