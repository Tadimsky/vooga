package vooga.rts.gamedesign;

import vooga.rts.gamedesign.sprite.rtsprite.Projectile;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;

public class Gun extends Weapon{

	public Gun(int damage, Projectile projectile, int range, Location3D center, int cooldown) {
		super(damage, projectile, range, center, cooldown);
	}
}
