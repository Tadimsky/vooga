package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
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

    private GatherStrategy myGatherStrategy;

    public Unit () {
        this(new Pixmap("images/sprites/soldier.png"), new Location3D(), new Dimension(0, 0), null,
             0, 100, InteractiveEntity.DEFAULT_BUILD_TIME);
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
                 double buildTime) {
        super(image, center, size, sound, playerID, health, buildTime);
        addActions();
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

    public void occupy (InteractiveEntity i) {
        i.getOccupied(this);
    }

    @Override
    public InteractiveEntity copy () {
        return new Unit(getImage(), getWorldLocation(), getSize(), getSound(), getPlayerID(),
                        getHealth(), getBuildTime());
    }

	public void setGatherStrategy(GatherStrategy gatherStrategy) {
		myGatherStrategy = gatherStrategy;
	}

}
