package vooga.rts.gamedesign.sprite.gamesprites;

import java.awt.Dimension;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.manager.GameResourceManager;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;


/**
 * This represents all resources that can be gathered by workers.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */

public class Resource extends GameEntity implements IGatherable {

    private static GameResourceManager myGameResourceManager;
    private String myType;

    /**
     * Creates a new resource
     * 
     * @param image is the image of the resource
     * @param center is the location of the resource
     * @param size is the size of the resource
     * @param playerID is the team the resource is on. Will be set to 0 to
     *        signify that the resource is not on anyone's team
     * @param health is the value contained in the resource. When this value
     *        becomes 0, the resource will disappear because it will not have any value
     *        left
     */
    public Resource (Pixmap image,
                     Location3D center,
                     Dimension size,
                     int playerID,
                     int health,
                     String type) {
        super(image, center, size, playerID, health);
        myType = type;
    }

    public void setGameResourceManager (GameResourceManager gameResourceManager) {
        myGameResourceManager = gameResourceManager;
    }

    @Override
    public void getGathered (int playerID, int gatherAmount) {
        changeHealth(gatherAmount);
        System.out.println("resource health: " + getHealth());
        // TODO: Remove resource properly
        if (isDead()) {
            setVisible(false);
            myGameResourceManager.assignResoure(this, playerID);
        }
    }
    
    /**
     * Returns the type of resource.
     * @return the type of resource
     */
    public String getType () {
        return myType;
    }
}
