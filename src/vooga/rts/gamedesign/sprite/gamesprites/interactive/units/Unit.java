package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import vooga.rts.commands.ClickCommand;
import vooga.rts.commands.Command;
import vooga.rts.commands.DragCommand;
import vooga.rts.controller.PositionCommand;
import vooga.rts.action.Action;
import vooga.rts.action.InteractiveAction;
import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IGatherable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.IOccupiable;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.strategy.gatherstrategy.CannotGather;
import vooga.rts.gamedesign.strategy.gatherstrategy.GatherStrategy;
import vooga.rts.gamedesign.strategy.occupystrategy.CannotOccupy;
import vooga.rts.gamedesign.strategy.occupystrategy.OccupyStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Camera;
import vooga.rts.util.Location;
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

	private static UpgradeTree myUpgradeTree;
	private List<GameSprite> myKills; // TODO: WHAT TYPE SHOULD IT BE??
	// private boolean myIsLeftSelected; // TODO: also need the same thing for
	// Projectiles
	// private boolean myIsRightSelected; // TODO: should be observing the mouse
	// action instead!!
	// private PathingHelper myPather;

	private GatherStrategy myGatherStrategy;

	private OccupyStrategy myOccupyStrategy;

	public Unit() {
		this(null, new Location3D(), new Dimension(0, 0), null, 0, 100);
	}

	/**
	 * Creates a new unit with an image, location, size, sound, teamID and
	 * health
	 * 
	 * @param image
	 *            is the image of the unit
	 * @param center
	 *            is the position of the unit on the map
	 * @param size
	 *            is the size of the unit
	 * @param sound
	 *            is the sound the unit makes
	 * @param playerID
	 *            is the ID for the team that the unit is on
	 * @param health
	 *            is the max health of the unit
	 */
	public Unit(Pixmap image, Location3D center, Dimension size, Sound sound,
			int playerID, int health) {
		super(image, center, size, sound, playerID, health);
		// myPather = new PathingHelper();
		System.out.println(playerID + " " + health);
		myOccupyStrategy = new CannotOccupy();
		if (myUpgradeTree != null) {
			if (myUpgradeTree.getUsers().get(playerID) == null) {
				List<InteractiveEntity> entityGroup = new ArrayList<InteractiveEntity>();
				entityGroup.add(this);
				myUpgradeTree.getUsers().put(playerID, entityGroup);
			} else {
				List<InteractiveEntity> entityGroup = myUpgradeTree.getUsers()
						.get(playerID);
				entityGroup.add(this);
				myUpgradeTree.getUsers().put(playerID, entityGroup);
			}
		}
		addActions();
	}

	@Override
	public void addActions() {
		put("leftclick", new InteractiveAction(this) {
			private Location3D myLocation;

			@Override
			public void apply() {
				getEntity().move(myLocation);
			}

			@Override
			public void update(Command command) {
				PositionCommand click = (PositionCommand) command;
				myLocation = Camera.instance().viewtoWorld(click.getPosition());
			}
		});
	}

	@Override
	public InteractiveEntity copy() {
		return new Unit(getImage(), getWorldLocation(), getSize(), getSound(),
				getPlayerID(), getHealth());
	}

	@Override
	public UpgradeTree getUpgradeTree() {
		return myUpgradeTree;
	}

	/**
	 * Occupies an IOccupiable object specified by occupy strategy.
	 * 
	 * @param occupiable
	 */
	public void occupy(IOccupiable occupiable) {
		if (myOccupyStrategy.canOccupy(occupiable)) {
			occupiable.getOccupied(this);
		}
	}

	@Override
	public void setUpgradeTree(UpgradeTree upgradeTree, int playerID) {
		myUpgradeTree = upgradeTree;
		if (myUpgradeTree.getUsers().get(playerID) == null) {
			List<InteractiveEntity> entityGroup = new ArrayList<InteractiveEntity>();
			entityGroup.add(this);
			myUpgradeTree.getUsers().put(playerID, entityGroup);
		} else {
			List<InteractiveEntity> entityGroup = myUpgradeTree.getUsers().get(
					playerID);
			entityGroup.add(this);
			myUpgradeTree.getUsers().put(playerID, entityGroup);
		}
	}
}
