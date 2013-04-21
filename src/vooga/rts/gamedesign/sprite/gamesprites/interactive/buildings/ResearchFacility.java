package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;
import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
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
public class ResearchFacility extends Building {

    public ResearchFacility (Pixmap image,
                             Location3D center,
                             Dimension size,
                             Sound sound,
                             int playerID,
                             int health,
                             double buildTime) {
        super(image, center, size, sound, playerID, health, buildTime);
    }

    @Override
    public void getOccupied (Unit unit) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addActions () {
        // TODO Auto-generated method stub
        
    }

}
