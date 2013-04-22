package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;

import vooga.rts.gamedesign.strategy.occupystrategy.CanBeOccupied;
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
public class Garrison extends Building {

    public Garrison(Pixmap image, Location3D center, Dimension size, Sound sound,
                    int playerID, int health, double buildTime) {
        super(image, center, size, sound, playerID, health, buildTime, null);
    }
    
    /*public void addOccupyActions(final Building building) {
    	getActions().add(new OccupyAction("puke",null,"I puke out all I have"){
            @Override
            public void apply(int playerID) {
            	getGameUnitManager().removeEntityUnit(building);
            	getOccupyStrategy().setOccupierID(0);
            	getOccupyStrategy().setOccupiers(new ArrayList<Unit>());
            }
    	});
    }*/
    
    /**
     * TESTING PURPOSE
     */
    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);
        if(getOccupyStrategy().getOccupiers().size() == 5) { 
            try {
				//getActions().get(0).apply(getOccupyStrategy().getOccupierID());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    @Override
    public void addActions () {
        // TODO Auto-generated method stub
        
    }
}