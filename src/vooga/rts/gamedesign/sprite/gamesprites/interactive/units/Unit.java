package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.commands.DragCommand;
import vooga.rts.commands.PositionCommand;
import vooga.rts.action.Action;
import vooga.rts.action.InteractiveAction;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.strategy.gatherstrategy.CannotGather;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * This class is an extension of InteractiveEntity, and represents shapes that
 * have the potential to gather resources (contains GatherStrategy) and the
 * potential to be occupied (contains OccupyStrategy). The movement of Units are
 * defined by the AI Engine.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class Unit extends InteractiveEntity {
	
	public static final Dimension DEFAULT_SIZE = new Dimension(50,50);

    public Unit () {
        this(new Pixmap("sprites/soldier.png"), new Location3D(), new Dimension(0, 0), null, 0, 100,
             InteractiveEntity.DEFAULT_BUILD_TIME, InteractiveEntity.DEFAULT_INTERACTIVEENTITY_SPEED);
    }

    /**
     * Creates a new unit with an image, location, size, sound, teamID,
     * health, and upgrade tree
     * 
     * @param image
     *        is the image of the unit
     * @param center
     *        is the position of the unit on the map
     * @param size
     *        is the size of the unit
     * @param sound
     *        is the sound the unit makes
     * @param playerID
     *        is the ID for the team that the unit is on
     * @param health
     *        is the max health of the unit
     */
    public Unit (Pixmap image,
                 Location3D center,
                 Dimension size,
                 Sound sound,
                 int playerID,
                 int health,
                 double buildTime, int speed) {
        super(image, center, size, sound, playerID, health, buildTime);
        setSpeed(speed);
        addActions();
    }
    
    public Unit(Pixmap image, Sound sound, int health, double buildTime, int speed){
    	this(image, InteractiveEntity.DEFAULT_LOCATION, DEFAULT_SIZE, sound, InteractiveEntity.DEFAULT_PLAYERID, health, buildTime, speed);
    	
    }

    @Override
    public void addActions () {
        put(ClickCommand.LEFT_CLICK, new InteractiveAction(this) {
            private Location3D myLocation;

            @Override
            public void apply () {
                getEntity().move(myLocation);
            }

            @Override
            public void update (Command command) {
                ClickCommand click = (ClickCommand) command;
                myLocation = Camera.instance().viewtoWorld(click.getPosition());
            }
        });
    }

    public void occupy(InteractiveEntity i) {
    	i.getOccupied(this);
    }

    @Override
    public InteractiveEntity copy () {
        return new Unit(getImage(), getWorldLocation(), getSize(), getSound(), getPlayerID(),
                        getHealth(), getBuildTime(), getSpeed());
    }
    
}
