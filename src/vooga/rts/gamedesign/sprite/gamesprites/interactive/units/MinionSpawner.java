package vooga.rts.gamedesign.sprite.gamesprites.interactive.units;

import java.awt.Dimension;


import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class MinionSpawner extends Unit {

  public MinionSpawner(Pixmap image, Location3D center, Dimension size,
			Sound sound, int playerID, int health, double buildTime) {
		super(image, center, size, sound, playerID, health, buildTime);
		// TODO Auto-generated constructor stub
	}


}