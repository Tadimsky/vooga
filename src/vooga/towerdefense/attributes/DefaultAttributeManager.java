package vooga.towerdefense.attributes;

import vooga.towerdefense.factories.definitions.ProjectileDefinition;
import vooga.towerdefense.factories.examplesfactories.ExampleDosProjectileFactory;

/**
 * Predefined attributeManager, used for testing purposes only.
 * 
 *
 */
public class DefaultAttributeManager extends AttributeManager {
	
	public DefaultAttributeManager(){
        this.addAttribute(new Attribute(AttributeConstants.ATTACK_RADIUS, 300.0));
        this.addAttribute(new Attribute(AttributeConstants.DIRECTION, 50.0));
        this.addAttribute(new Attribute(AttributeConstants.ATTACK_INTERVAL, 30.0));
        this.addAttribute(new Attribute(AttributeConstants.NUM_OF_TARGETS, 1.0));
        this.addAttribute(new Attribute(AttributeConstants.ATTACK_RADIUS, 200.0));
        this.addAttribute(new Attribute(AttributeConstants.MOVE_SPEED, 1.0));
        this.addAttribute(new Attribute(AttributeConstants.AURA_EFFECT, -10.0));
        this.addAttribute(new Attribute(AttributeConstants.TILES_WIDE, 1.0));
        this.addAttribute(new Attribute(AttributeConstants.TILES_TALL, 1.0));
        this.addAttribute(new Attribute(AttributeConstants.HEALTH, 100.0));

        this.addGameElementFactory(AttributeConstants.PROJECTILE_FACTORY,(new ExampleDosProjectileFactory("projectilefactory", new ProjectileDefinition())));
	}
	

}
