package vooga.rts.gamedesign.sprite.gamesprites.interactive.buildings;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.units.Unit;
import vooga.rts.gamedesign.upgrades.UpgradeTree;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;
import vooga.rts.util.Sound;


/**
 * A custom implementation of building. Instead of specifying type in XML, you can
 * specify type with default values here. 
 * @author Francesco Agosti
 *
 */
public class Tower extends Building {

	private static Pixmap DEFAULTPIX = new Pixmap("barracks.jpeg");
	private static Location3D DEFAULTLOC = new Location3D(100,100,100);
	private static Dimension DEFAULTSIZE = new Dimension(50,50);
	private static Sound DEFAULTSOUND = new Sound("squirtle.wav");
	private static int DEFAULTHEALTH = 100;
	private static int DEFAULTBUILDTIME = 10;
	private static int NOTEAM = 0;
	
	public Tower(int playerID) {
		super(DEFAULTPIX, DEFAULTLOC, DEFAULTSIZE, DEFAULTSOUND, playerID, DEFAULTHEALTH, DEFAULTBUILDTIME);
		
	}
	
	public Tower(){
		this(NOTEAM);
	}

	@Override
	public void getOccupied(Unit unit) {
	

	}

}
