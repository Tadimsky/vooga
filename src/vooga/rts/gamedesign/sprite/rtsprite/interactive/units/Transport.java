package vooga.rts.gamedesign.sprite.rtsprite.interactive.units;

import java.awt.Dimension;

import vooga.rts.gamedesign.sprite.rtsprite.IAttackable;
import vooga.rts.gamedesign.sprite.rtsprite.IGatherable;
import vooga.rts.gamedesign.sprite.rtsprite.RTSprite;
import vooga.rts.gamedesign.sprite.rtsprite.RTSpriteVisitor;
import vooga.rts.gamedesign.sprite.rtsprite.interactive.IOccupiable;
import vooga.rts.util.Location;
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
public class Transport extends Units implements IOccupiable {

	public Transport(Pixmap image, Location center, Dimension size,
			Sound sound, int teamID, int health) {
		super(image, center, size, sound, teamID, health);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void move(Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int calculateDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void changeHealth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RTSprite rtSprite) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IAttackable a) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IOccupiable o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IGatherable g) {
		// TODO Auto-generated method stub
		
	}
}