package vooga.rts.gamedesign.weapon;

import vooga.rts.gamedesign.sprite.gamesprites.Projectile;
import vooga.rts.util.Location3D;

public class Gun extends Weapon{
// YOLO

	public Gun(Projectile projectile, int range, Location3D center, int cooldown) {
		super(projectile, range, center, cooldown);
	}
}
