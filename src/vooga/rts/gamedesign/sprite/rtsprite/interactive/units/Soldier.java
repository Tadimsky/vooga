package vooga.rts.gamedesign.sprite.rtsprite.interactive.units;

import java.awt.Dimension;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * 
 * @author Kevin Oh
 * 
 */
public class Soldier extends Unit {
    /**
     * for this implementation of visit where the soldier visits a IOccupiable,
     * the soldier occupies the IOccupiable RTSprite.
     * Code: would call myOccupyStrategy.occupy(RTSprite);
     */

    //default values
    public static Pixmap DEFAULT_IMAGE = new Pixmap(ResourceManager.instance().loadFile("images/sprites/soldier.png"));
    public static Location3D DEFAULT_LOCATION = new Location3D();
    public static Dimension DEFAULT_SIZE = new Dimension(90,90);
    public static Sound DEFAULT_SOUND = null;
    public static int DEFAULT_PLAYERID = 1;
    public static int DEFAULT_HEALTH = 100;

    // private int myHealth; //TESTING PURPOSE
    public Soldier() {
        this(DEFAULT_IMAGE, DEFAULT_LOCATION, DEFAULT_SIZE, DEFAULT_SOUND, DEFAULT_PLAYERID, DEFAULT_HEALTH);
    }
    
    public Soldier(Pixmap image, Location3D center, Dimension size, Sound sound, int playerID, int health) {
        super(image, center, size, sound, playerID, health);
    }

    public void upgradeHealth (int armor) { // TESTING PURPOSE
        setHealth(getHealth() + armor);
    }
    /*
     * Returns a copy of the default soldier. 
     */
    public Soldier copy() {
        //System.out.println("soldier's copy method called");
        //return null;
        return new Soldier();
    }
    
}
