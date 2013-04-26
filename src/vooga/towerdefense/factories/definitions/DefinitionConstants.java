package vooga.towerdefense.factories.definitions;

import java.awt.Dimension;

import vooga.towerdefense.util.Location;
import vooga.towerdefense.util.Pixmap;

/**
 * List of default definitions used for testing factories.
 * 
 * @author XuRui
 *
 */
public class DefinitionConstants {
	
	//Projectile definitions
	public static final int DEFAULT_PROJECTILE_WIDTH = 20;
	public static final Pixmap DEFAULT_PROJECTILE_IMAGE = new Pixmap("fireball.gif");
	public static final Dimension DEFAULT_PROJECTILE_SIZE = new Dimension(DEFAULT_PROJECTILE_WIDTH,
			DEFAULT_PROJECTILE_WIDTH);
	public static final Location DEFAULT_PROJECTILE_LOCATION = new Location(200, 100);
	public static final String DEFAULT_PROJECTILE_NAME = "projectile";

	
	
	//Tower definitions
	public static final int DEFAULT_TOWER_WIDTH = 50;
	public static final Pixmap DEFAULT_TOWER_IMAGE = new Pixmap("Duvall.jpg");
	public static final Location DEFAULT_TOWER_LOCATION = new Location(200, 100);
	public static final Dimension DEFAULT_TOWER_SIZE = new Dimension(50, 50);
	public static final String DEFAULT_TOWER_NAME = "tower";
	
	//Unit definitions
	public static final int DEFAULT_UNIT_WIDTH = 50;
	public static final Pixmap DEFAULT_UNIT_IMAGE = new Pixmap("Troll_Sprite.png");
	public static final Location DEFAULT_UNIT_LOCATION = new Location(200, 100);
	public static final Dimension DEFAULT_UNIT_SIZE = new Dimension(50, 50);
	public static final String DEFAULT_UNIT_NAME = "unit";



	
	

}
