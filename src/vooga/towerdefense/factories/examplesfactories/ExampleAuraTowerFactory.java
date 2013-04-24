package vooga.towerdefense.factories.examplesfactories;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.FindTargets;
import vooga.towerdefense.action.actionlist.LaunchProjectile;
import vooga.towerdefense.attributes.AttributeConstants;
import vooga.towerdefense.attributes.AttributeManager;
import vooga.towerdefense.factories.GameElementFactory;
import vooga.towerdefense.factories.definitions.ExampleTowerDefinition;
import vooga.towerdefense.factories.definitions.GameElementDefinition;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;


/**
 * Example tower factory that can modify attributes of units within attack radius.
 * Actions and attributes are manually defined here.
 * 
 * @author Matthew Roy
 * @author Xu Rui
 */
public class ExampleAuraTowerFactory extends GameElementFactory {

    GameMap myMap;

    /**
     * @param name
     * @param def
     */
    public ExampleAuraTowerFactory (GameMap map, String name, GameElementDefinition def) {
        super(name, def);
        myMap = map;
    }

    public GameElement createElement (Location putHere) {
    	//Pixmap image, Location center, Dimension size, String type
        GameElementDefinition def = new ExampleTowerDefinition(new Pixmap("Tower.gif"), putHere, new Dimension(50,50), "tower");
        AttributeManager AM = getDefaultAM();
        
        GameElement myTower;
        if (putHere != null) {
            myTower = new GameElement(def.getImage(), putHere,
                                      new Dimension(50, 50), AM);
        }
        else {
            myTower = new GameElement(def.getImage(),
                                      def.getCenter(), def.getSize(), AM);
        }
        
        myTower.addActions(createActions(myTower));
        return myTower;
    }
    
    /**
     * Manually add game element actions (for testing only actions will be predefined in level editor)
     */
    @Override
    public List<Action> createActions(GameElement element) {
    	 ArrayList<Action> actions = new ArrayList<Action>();
         FindTargets findTargets =
                 new FindTargets(myMap, element.getCenter(), 
                		 element.getAttributeManager().getAttribute(AttributeConstants.ATTACK_RADIUS));
         findTargets.addFollowUpAction(new LaunchProjectile(myMap, element.getCenter(), 
        		 new ExampleDosProjectileFactory()));
         		/*new ModifyAttributeValue(AM
                 .getAttribute(AttributeConstants.AURA_EFFECT), AttributeConstants.HEALTH));*/
         actions.add(findTargets);
        return actions;
    }

}
