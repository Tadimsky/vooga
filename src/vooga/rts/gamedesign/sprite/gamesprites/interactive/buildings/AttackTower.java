package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;
import java.awt.Dimension;

/**
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu 
 *
 */
public class AttackTower extends Building {

	public AttackTower(Pixmap image, Location3D center, Dimension size,
			Sound sound, int playerID, int health) {
		super(image, center, size, sound, playerID, health);
		// TODO Auto-generated constructor stub
	}

    @Override
    public void getOccupied (Unit unit) {
        // TODO Auto-generated method stub
        
    }


	
}