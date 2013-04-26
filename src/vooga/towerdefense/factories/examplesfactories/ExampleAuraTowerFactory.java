package vooga.towerdefense.factories.examplesfactories;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import vooga.towerdefense.action.Action;
import vooga.towerdefense.action.FindTargets;
import vooga.towerdefense.action.actionlist.LaunchProjectile;
import vooga.towerdefense.attributes.AttributeConstants;
import vooga.towerdefense.attributes.AttributeManager;
import vooga.towerdefense.factories.definitions.GameElementDefinition;
import vooga.towerdefense.factories.definitions.ProjectileDefinition;
import vooga.towerdefense.factories.definitions.TowerDefinition;
import vooga.towerdefense.factories.elementfactories.GameElementFactory;
import vooga.towerdefense.gameElements.GameElement;
import vooga.towerdefense.model.GameMap;
import vooga.towerdefense.util.Location;


/**
 * Example tower factory that can modify attributes of units within attack radius.
 * Actions and attributes are manually defined here.
 * 
 * @author Matthew Roy
 * @author Xu Rui
 */
public class ExampleAuraTowerFactory extends GameElementFactory {

	GameMap myMap;
	GameElementDefinition myDef;

	/**
	 * @param name
	 * @param def
	 */
	public ExampleAuraTowerFactory (String name, TowerDefinition def) {
		super(name, def);
		myMap = getMap();
		myDef = new TowerDefinition();

	}

	public GameElement createElement (Location putHere) {
		AttributeManager AM = getDefaultAM();

		GameElement myTower;
		if (putHere != null) {
			myTower = new GameElement(myDef.getImage(), putHere,
					new Dimension(50, 50), AM);
		}
		else {
			myTower = new GameElement(myDef.getImage(),
					myDef.getCenter(), myDef.getSize(), AM);
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
				 new ExampleDosProjectileFactory("projectileFactory", new ProjectileDefinition())));
		 /*new ModifyAttributeValue(AM
                 .getAttribute(AttributeConstants.AURA_EFFECT), AttributeConstants.HEALTH));*/
		 actions.add(findTargets);
		 return actions;
	 }

}
