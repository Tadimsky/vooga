package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.attackstrategy.CanAttack;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Information;
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

    // default values
    public static Pixmap DEFAULT_IMAGE = new Pixmap("images/sprites/soldier.png");
    public static Location3D DEFAULT_LOCATION = new Location3D();
    public static Dimension DEFAULT_SIZE = new Dimension(90, 90);
    public static Sound DEFAULT_SOUND = null;
    public static int DEFAULT_PLAYERID = 2;
    public static int DEFAULT_HEALTH = 100;
    public static int DEFAULT_SPEED = 100;

    // private int myHealth; //TESTING PURPOSE
    public Soldier () {
        this(DEFAULT_IMAGE, DEFAULT_LOCATION, DEFAULT_SIZE, DEFAULT_SOUND, DEFAULT_PLAYERID,
             DEFAULT_HEALTH, InteractiveEntity.DEFAULT_BUILD_TIME);
        // for testing.
        Information i =
                new Information("Marine", "I fear no darkness. I was born in it", "buttons/marine.png", null);
        setInfo(i);
    }

    public Soldier (Location3D location, int playerID) {
        this(DEFAULT_IMAGE, location, DEFAULT_SIZE, DEFAULT_SOUND, playerID, DEFAULT_HEALTH,
             InteractiveEntity.DEFAULT_BUILD_TIME);
    }

    public Soldier (Pixmap image,
                    Location3D center,
                    Dimension size,
                    Sound sound,
                    int playerID,
                    int health,
                    double buildTime) {
        super(image, center, size, sound, playerID, health, buildTime, DEFAULT_SPEED);
        setAttackStrategy(new CanAttack(getWorldLocation(), playerID));
    }

    public void upgradeHealth (int armor) { // TESTING PURPOSE
        setHealth(getHealth() + armor);
    }
}
