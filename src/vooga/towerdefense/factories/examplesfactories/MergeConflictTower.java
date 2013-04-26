package vooga.towerdefense.factories.examplesfactories;

import java.awt.Dimension;
import java.util.ArrayList;
import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.FindTargets;
import vooga.towerdefense.action.actionlist.LaunchProjectile;
import vooga.towerdefense.action.tobetested.RandomChance;
import vooga.towerdefense.attributes.AttributeConstants;
import vooga.towerdefense.attributes.AttributeConstantsEnum;
import vooga.towerdefense.attributes.AttributeManager;
import vooga.towerdefense.factories.definitions.ProjectileDefinition;
import vooga.towerdefense.factories.definitions.TowerDefinition;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;

/**
 * Example tower used for testing purposes only.
 * 
 * @author Matthew Roy
 *
 */
public class MergeConflictTower extends GameElementFactory {



    /**
     * @param name
     * @param def
     */
    public MergeConflictTower (GameMap map, String name, TowerDefinition def) {
        super(name, def);
        initialize(map);
    }

    @Override
    public GameElement createElement (Location putHere) {
        TowerDefinition def = new TowerDefinition();
        AttributeManager AM = getDefaultAM();

        Pixmap tImage = new Pixmap("github.png");
        GameElement myTower;
        if (putHere != null) {
            myTower = new GameElement(tImage, putHere,
                                      new Dimension(100, 100), AM);
        }
        else {
            myTower = new GameElement(def.getImage(),
                                      def.getCenter(), def.getSize(), AM);
        }

        ArrayList<Action> actions = new ArrayList<Action>();
        FindTargets findTargets =
                new FindTargets(getMap(), myTower.getCenter(), AM.getAttribute(AttributeConstants.ATTACK_RADIUS));
        Action randomFiring = new RandomChance(0.01);
        ExampleDosProjectileFactory coolStoryBro = new ExampleDosProjectileFactory("projectilefactory", new ProjectileDefinition());
        coolStoryBro.initialize(getMap());
        //Action launchProjectile = new MakeElement(getMap(), myTower.getCenter(), new ExampleDosProjectileFactory());
        Action launchProjectile = new LaunchProjectile(getMap(), putHere, new ExampleDosProjectileFactory("projectilefactory", new ProjectileDefinition()));
        findTargets.addFollowUpAction(launchProjectile);
        randomFiring.addFollowUpAction(findTargets);
        actions.add(randomFiring);
        //actions.add(launchProjectile);

        myTower.addActions(actions);
        return myTower;
    }
    


    
    
}
