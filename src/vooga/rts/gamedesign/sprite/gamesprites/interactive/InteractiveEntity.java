package vooga.rts.gamedesign.sprite.gamesprites.interactive;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import vooga.rts.action.Action;
import vooga.rts.action.IActOn;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.GameEntity;
import vooga.rts.gamedesign.sprite.gamesprites.IAttackable;
import vooga.rts.gamedesign.sprite.gamesprites.Projectile;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.state.AttackingState;
import vooga.rts.gamedesign.strategy.attackstrategy.AttackStrategy;
import vooga.rts.gamedesign.strategy.attackstrategy.CannotAttack;
import vooga.rts.gamedesign.strategy.production.CannotProduce;
import vooga.rts.gamedesign.strategy.production.ProductionStrategy;
import vooga.rts.gamedesign.upgrades.UpgradeNode;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Camera;
import vooga.rts.util.DelayedTask;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * This class is the extension of GameEntity. It represents shapes that are able
 * to upgrade (to either stat of its current properties or add new properties)
 * and attack others.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public abstract class InteractiveEntity extends GameEntity implements IAttackable, IActOn {

    private static final int LOCATION_OFFSET = 20;
    private static int DEFAULT_INTERACTIVEENTITY_SPEED = 150;
    private boolean isSelected;
    private UpgradeTree myUpgradeTree;
    private Sound mySound;
    private AttackStrategy myAttackStrategy;
    private ProductionStrategy myProductionStrategy;
    private int myArmor;
    private Map<String, Action> myActions;
    private List<DelayedTask> myTasks;

    /**
     * Creates a new interactive entity.
     * 
     * @param image
     *        is the image of the interactive entity
     * @param center
     *        is the location of the interactive entity
     * @param size
     *        is the dimension of the interactive entity
     * @param sound
     *        is the sound the interactive entity makes
     * @param teamID
     *        is the ID of the team that the interactive entity is on
     * @param health
     *        is the health of the interactive entity
     */
    public InteractiveEntity (Pixmap image,
                              Location3D center,
                              Dimension size,
                              Sound sound,
                              int playerID,
                              int health) {
        super(image, center, size, playerID, health);
        // myMakers = new HashMap<String, Factory>(); //WHERE SHOULD THIS GO?
        mySound = sound;
        myAttackStrategy = new CannotAttack();
        myProductionStrategy = new CannotProduce();
        myActions = new HashMap<String, Action>();
        isSelected = false;
        myTasks = new ArrayList<DelayedTask>();
    }

    public void addAction (String command, Action action) {
        myActions.put(command, action);
    }

    public abstract void addActions ();

    public void addTask (DelayedTask dt) {
        myTasks.add(dt);
    }

    /**
     * This method specifies that the interactive entity is attacking an
     * IAttackable. It checks to see if the IAttackable is in its range, it sets
     * the state of the interactive entity to attacking, and then it attacks the
     * IAttackable if the state of the interactive entity lets it attack.
     * 
     * @param attackable
     *        is the IAttackable that is being attacked.
     */
    public void attack (IAttackable attackable) {
        double distance =
                Math.sqrt(Math.pow(getWorldLocation().getX() -
                                   ((InteractiveEntity) attackable).getWorldLocation().getX(), 2) +
                          Math.pow(getWorldLocation().getY() -
                                   ((InteractiveEntity) attackable).getWorldLocation().getY(), 2));
        if (!this.isDead()) {
            // getEntityState().setAttackingState(AttackingState.ATTACKING);
            if (getEntityState().getAttackingState() != AttackingState.WAITING &&
                getEntityState().getAttackingState() != AttackingState.ATTACKING) {
                getEntityState().attack();
            }
            // setVelocity(getVelocity().getAngle(), 0);
            // getGameState().setMovementState(MovementState.STATIONARY);
            if (getEntityState().canAttack()) {
                myAttackStrategy.attack(attackable, distance);
            }
        }
    }

    public int calculateDamage (int damage) {
        return damage * (1 - (myArmor / (myArmor + 100)));
    }

    public boolean containsInput (Command command) {
        return myActions.containsKey(command.getMethodName());
    }

    /*
     * Ze clone method
     */
    // TODO: Make abstract

    public abstract InteractiveEntity copy ();

    public Action getAction (Command command) {
        System.out.println("getting");
        return myActions.get(command.getMethodName());
    }

    /**
     * This method specifies that the interactive entity is getting attacked so
     * it calls the attack method of the interactive entity on itself.
     * 
     * @param interactiveEntity
     *        is the interactive entity that is attacking this interactive
     *        entity
     */
    public void getAttacked (InteractiveEntity interactiveEntity) {
        interactiveEntity.attack(this);
    }

    /**
     * Returns the current attack strategy of the interactive
     * 
     * @return the current attack strategy
     */
    public AttackStrategy getAttackStrategy () {
        return myAttackStrategy;
    }

    /**
     * Returns the sound that the interactive entity makes.
     * 
     * @return the sound of the interactive entity
     */

    public Sound getSound () {
        return mySound;
    }

    public int getSpeed () {
        return DEFAULT_INTERACTIVEENTITY_SPEED;
    }

    public UpgradeTree getTree () {
        return myUpgradeTree;
    }

    public ProductionStrategy getProductionStrategy () {
        return myProductionStrategy;
    }

    public void setProductionStrategy (ProductionStrategy productionStrategy) {
        myProductionStrategy = productionStrategy;
    }

    /**
     * Returns the upgrade tree for the interactive entity.
     * 
     * @return the upgrade tree for the interactive entity
     */

    public UpgradeTree getUpgradeTree () {
        return myUpgradeTree;
    }

    /**
     * Sees whether the passed in InteractiveEntity is an enemy by checking if
     * player IDs do not match
     * 
     * @param InteractiveEntity
     *        other - the other InteractiveEntity to compare
     * @return whether the other InteractiveEntity is an enemy
     */
    public boolean isEnemy (InteractiveEntity other) {
        return getPlayerID() != other.getPlayerID();
    }

    @Override
    public void paint (Graphics2D pen) {
        // pen.rotate(getVelocity().getAngle());

        // should probably use the getBottom, getHeight etc...implement them
        Point2D selectLocation = Camera.instance().worldToView(getWorldLocation());

        pen.drawRect((int) selectLocation.getX() - LOCATION_OFFSET,
                     (int) (selectLocation.getY() - 5 * LOCATION_OFFSET), 50, 5);
        Rectangle2D healthBar =
                new Rectangle2D.Double((int) selectLocation.getX() - LOCATION_OFFSET,
                                       (int) (selectLocation.getY() - 5 * LOCATION_OFFSET),
                                       50 * getHealth() / getMaxHealth(), 5);
        float width = (float) (healthBar.getWidth() * (getHealth() / getMaxHealth()));
        pen.setPaint(new GradientPaint((float) healthBar.getX() - width, (float) healthBar
                .getMaxY(), Color.RED, (float) healthBar.getMaxX(), (float) healthBar.getMaxY(),
                                       Color.GREEN));
        pen.fill(healthBar);
        pen.setColor(Color.black);

        if (isSelected) {
            Ellipse2D.Double selectedCircle =
                    new Ellipse2D.Double(selectLocation.getX() - LOCATION_OFFSET,
                                         selectLocation.getY() + LOCATION_OFFSET, 50, 30);
            pen.fill(selectedCircle);
        }
        super.paint(pen);
        if (myAttackStrategy.getCanAttack() && !getAttackStrategy().getWeapons().isEmpty()) {
            for (Projectile p : myAttackStrategy.getWeapons()
                    .get(myAttackStrategy.getWeaponIndex()).getProjectiles()) {
                p.paint(pen);
            }
        }
    }

    public void put (String name, Action action) { // Might just use a putter
        myActions.put(name, action);
    }

    /**
     * If the passed in parameter is another InteractiveEntity, checks to see if
     * it is a Building and can be occupied, checks to see if it is an enemy,
     * and if so, switches to attack state. Defaults to move to the center of
     * the other InteractiveEntity
     * 
     * @param other
     *        - the other InteractiveEntity
     */
    public void recognize (InteractiveEntity other) {
        if (other instanceof Building) {
            // occupy or do something
        }
        if (isEnemy(other)) {
            // switch to attack state
        }
        move(other.getWorldLocation());
    }

    // below are the recognize methods to handle different input parameters from
    // controller
    /**
     * If the passed in parameter is type Location3D, moves the
     * InteractiveEntity to that location
     * 
     * @param location
     *        - the location to move to
     */
    public void recognize (Location3D location) {
        move(location);
    }

    /***
     * Sets the isSelected boolean to the passed in bool value.
     */
    public boolean select (boolean selected) {

        if (selected && getState().canSelect()) {
            isSelected = selected;
        }
        if (!selected) {
            isSelected = selected;
        }
        return isSelected;
    }

    /**
     * Sets the attack strategy for an interactive. Can set the interactive to
     * CanAttack or to CannotAttack and then can specify how it would attack.
     * 
     * @param newStrategy
     *        is the new attack strategy that the interactive will have
     */
    public void setAttackStrategy (AttackStrategy newStrategy) {
        myAttackStrategy = newStrategy;
    }

    public void setUpgradeTree (UpgradeTree upgradeTree, int playerID) {
        myUpgradeTree = upgradeTree;
    }

    //
    // public Action findAction(String name) {
    // for (Action a: myActions) {
    // if (a.getName().equals(name)) {
    // return a;
    // }
    // }
    // return null;
    // }

    @Override
    public void update (double elapsedTime) {

        super.update(elapsedTime);

        Iterator<DelayedTask> it = myTasks.iterator();
        while (it.hasNext()) {
            DelayedTask dt = it.next();
            dt.update(elapsedTime);
            if (!dt.isActive()) {
                it.remove();
            }
        }

        if (myAttackStrategy.getCanAttack() && !getAttackStrategy().getWeapons().isEmpty()) {
            myAttackStrategy.getWeapons().get(myAttackStrategy.getWeaponIndex())
                    .setCenter(getWorldLocation());
            myAttackStrategy.getWeapons().get(myAttackStrategy.getWeaponIndex())
                    .update(elapsedTime);
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public void updateAction (Command command) {
        if (myActions.containsKey(command.getMethodName())) {
            Action action = myActions.get(command.getMethodName());
            action.update(command);
        }
    }

    /**
     * upgrades the interactive based on the selected upgrade
     * 
     * @param upgradeNode
     *        is the upgrade that the interactive will get
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public void upgrade (UpgradeNode upgradeNode) throws IllegalArgumentException,
                                                 SecurityException, IllegalAccessException,
                                                 InvocationTargetException, InstantiationException,
                                                 NoSuchMethodException {
        // upgradeNode.apply(upgradeNode.getUpgradeTree().getUsers());
    }

    public void setChanged () {
        super.setChanged();
    }

}
