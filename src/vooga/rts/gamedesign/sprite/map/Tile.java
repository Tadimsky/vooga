package vooga.rts.gamedesign.sprite.map;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.gamesprites.GameSprite;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * The background image for the map.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class Tile extends GameSprite {

	public Tile(Pixmap image, Location3D center, Dimension size) {
		super(image, center, size);
	}

	@Override
	public void update(double elapsedTime) {
		
	}
}