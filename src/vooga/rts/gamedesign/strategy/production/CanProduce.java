package vooga.rts.gamedesign.strategy.production;

import vooga.rts.action.InteractiveAction;
import vooga.rts.commands.Command;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings.Building;
import vooga.rts.gamedesign.state.GatherState;
import vooga.rts.gamedesign.state.ProducingState;
import vooga.rts.gamedesign.strategy.Strategy;
import vooga.rts.gamedesign.strategy.attackstrategy.CanAttack;
import vooga.rts.util.DelayedTask;
import vooga.rts.util.Location3D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements ProductionStrategy and is used as an instance in
 * InteractiveEntity for objects that are able to produce other
 * InteractiveEntity. The produce method in this class will specify how the
 * interactive will produce other units.
 * 
 * @author Kevin Oh
 * 
 */
public class CanProduce implements ProductionStrategy {
    private static final Location3D DEFAULT_RELATIVE_RALLY_POINT = new Location3D(50, 50, 0);

    private List<InteractiveEntity> myProducables;
    private Location3D myRallyPoint;
    private ProducingState myProduceState;

    /**
     * Creates a new production strategy that represents an entity that can
     * produce other entities. It is created with a list of entities that it can
     * produce and a rally point (where all the units created by this entity
     * will go).
     */
    public CanProduce (InteractiveEntity entity) {
        myProducables = new ArrayList<InteractiveEntity>();
        myRallyPoint = new Location3D();
        myProduceState = ProducingState.NOT_PRODUCING;
        setRallyPoint(entity);
    }

    /**
     * Sets the rally point of the entity that can produce so that units will
     * move to that point after they are created by the entity.
     * 
     * @param rallyPoint
     *        is the location where the units will go when they are created
     */
    public void setRallyPoint (Location3D rallyPoint) {
        myRallyPoint = rallyPoint;
    }

    /**
     * Sets the rally point based on the position of the entity.
     */
    public void setRallyPoint (InteractiveEntity entity) {
        setRallyPoint(new Location3D(entity.getWorldLocation().getX() +
                                     DEFAULT_RELATIVE_RALLY_POINT.getX(), entity.getWorldLocation()
                .getY() + DEFAULT_RELATIVE_RALLY_POINT.getY(), 0));
    }

    /**
     * Adds an interactive entity that can be produced to the list of this
     * entities producables.
     * 
     * @param producable
     *        is an entity that this production entity can create
     */
    public void addProducable (InteractiveEntity producable) {
        myProducables.add(producable);

    }

    /**
     * Creates production related actions to the InteractiveEntity passed in
     * for each type of InteractiveEntity that it is able to produce.
     */
    public void createProductionActions (final InteractiveEntity producer) {
        for (final InteractiveEntity producable : myProducables) {
            final String commandName = "make " + producable.getInfo().getName();
            producer.addAction(commandName, new InteractiveAction(producer) {
                @Override
                public void update (Command command) {
                }

                @Override
                public void apply () {
                    final InteractiveEntity unit = producable;
                    DelayedTask dt = new DelayedTask(unit.getBuildTime(), new Runnable() {
                        @Override
                        public void run () {
                            InteractiveEntity f = unit.copy();
                            f.setWorldLocation(producer.getWorldLocation());
                            producer.setChanged();
                            producer.notifyObservers(f);
                            f.move(myRallyPoint);
                            myProduceState = ProducingState.PRODUCING;
                            DelayedTask dt = new DelayedTask(unit.getBuildTime(), new Runnable() {
                                @Override
                                public void run () {
                                    InteractiveEntity f = unit.copy();
                                    f.setWorldLocation(producer.getWorldLocation());
                                    producer.setChanged();
                                    producer.notifyObservers(f);

                                    myProduceState = ProducingState.NOT_PRODUCING;
                                    f.move(myRallyPoint);

                                }
                            });

                            System.out.println("added");
                            producer.addQueueableTask(dt);
                        }
                    });
                    producer.addInfo(commandName, producable.getInfo());
                }
            });
        }

    }

    public ProducingState getProducingState () {
        return myProduceState;
    }

    /**
     * Applies this CanProduce strategy to another InteractiveEntity that is
     * passed in, by setting it as the InteractiveEntity's strategy and
     * recreating the actions.
     * 
     * @param other the InteractiveEntity that will receive the strategy.
     */
    public void affect (InteractiveEntity entity) {
        ProductionStrategy newProduction = new CanProduce(entity);
        newProduction.setProducables(getProducables());
        ((CanProduce) newProduction).createProductionActions(entity);
        entity.setProductionStrategy(newProduction);
    }

    @Override
    public void update (double elapsedTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void paint (Graphics2D pen) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<InteractiveEntity> getProducables () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProducables (List<InteractiveEntity> producables) {
        // TODO Auto-generated method stub

    }
}
