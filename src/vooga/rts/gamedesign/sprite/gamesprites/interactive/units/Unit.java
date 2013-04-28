package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import java.util.List;
import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.Resource;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.state.UnitState;
import vooga.rts.gamedesign.strategy.gatherstrategy.CanGather;
import vooga.rts.state.GameState;
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

    // default values
    public static final Pixmap DEFAULT_IMAGE = new Pixmap(
            "images/sprites/soldier.png");
    public static final Location3D DEFAULT_LOCATION = new Location3D();
    public static final Dimension DEFAULT_SIZE = new Dimension(90, 90);
    public static final Sound DEFAULT_SOUND = null;
    public static final int DEFAULT_PLAYERID = 1;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFUALT_GATHER_RADIUS = 500;

    public Unit () {
        this(DEFAULT_IMAGE, DEFAULT_LOCATION, DEFAULT_SIZE, DEFAULT_SOUND, DEFAULT_PLAYERID,
             DEFAULT_HEALTH, InteractiveEntity.DEFAULT_BUILD_TIME, InteractiveEntity.DEFAULT_SPEED);

    }

    /**
     * Creates a new unit with an image, location, size, sound, teamID, health,
     * and upgrade tree
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
                 double buildTime,
                 int speed) {
        super(image, center, size, sound, playerID, health, buildTime);
        setSpeed(speed);
        addActions();
    }

    public Unit (Pixmap image, Sound sound, int health, double buildTime, int speed) {
        this(image, InteractiveEntity.DEFAULT_LOCATION, DEFAULT_SIZE, sound,
             InteractiveEntity.DEFAULT_PLAYERID, health, buildTime, speed);

    }

    @Override
    public void addActions () {
        addAction(ClickCommand.LEFT_CLICK, new InteractiveAction(this) {
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

    /**
     * The unit occupies the interactive entity that is passed in.
     * 
     * @param i
     *        is the interactive entity that will be occupied
     */
    public void occupy (InteractiveEntity i) {
        i.getOccupied(this);
    }

    @Override
    public InteractiveEntity copy () {
        Unit copyUnit =
                new Unit(getImage(), getWorldLocation(), getSize(), getSound(), getPlayerID(),
                         getHealth(), getBuildTime(), getSpeed());
        transmitProperties(copyUnit);
        return copyUnit;
    }

    @Override
    public void updateAction (Command command) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update (double elapsedTime) {
        if (getEntityState().getUnitState() == UnitState.OCCUPY) {
            this.occupy((InteractiveEntity) getTargetEntity());
        }
        if(getGatherStrategy() instanceof CanGather) {
            if (getEntityState().getUnitState() == UnitState.GATHER) {
                this.gather((IGatherable) getTargetEntity());
                if(getTargetEntity().isDead()) {
                    List<Resource> resources = GameState.getMap().getResources()
                            .getInArea(getWorldLocation(), DEFUALT_GATHER_RADIUS);
                    if (!resources.isEmpty()) {
                        System.out.println("yayayayyaya");
                        Resource resource = resources.get(0);
                        setGoalLocation(resource.getWorldLocation());
                        setTargetEntity(resource);
                    }
                }
            }
        }
        super.update(elapsedTime);
    }
}
