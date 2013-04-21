package vooga.rts.gamedesign.sprite.gamesprites;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import vooga.rts.gamedesign.sprite.gamesprites.interactive.InteractiveEntity;
import vooga.rts.resourcemanager.ResourceManager;
import vooga.rts.util.DelayedTask;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

/**
 * This class is the generic abstract class for all types of projectiles that
 * can be fired from a weapon by an InteractiveEntity. The Projectile’s health
 * is the time the projectile can exist on the map before it vanishes. It also
 * vanishes after it collides with a GameEntity.
 * 
 * @author Ryan Fishel
 * @author Kevin Oh
 * @author Francesco Agosti
 * @author Wenshun Liu
 * 
 */
public class Projectile extends GameEntity {
	// Default speed
	public static int DEFAULT_PROJECTILE_SPEED = 800;
	public static Pixmap DEFAULT_PIC = new Pixmap(ResourceManager.getInstance()
			.<BufferedImage> getFile("images/bullet.png", BufferedImage.class));
	public static Dimension DEFAULT_DIMENSION = new Dimension(20, 20);
	public static int DEFAULT_DAMAGE = 10;
	public static int DEFAULT_HEALTH = 6;

	private int myDamage;
	private InteractiveEntity myTarget;
	
	private DelayedTask myTimer;

	/**
	 * Creates a new projectile.
	 * 
	 * @param pixmap
	 *            is the image of the projectile
	 * @param loc
	 *            is the location where the projectile is created
	 * @param size
	 *            is the size of the projectile
	 * @param playerID
	 *            is the team the projectile is on
	 * @param damage
	 *            is the damage that the projectile will infilict on its target
	 * @param health
	 *            is the amount of time a projectile will travel for if it does
	 *            not hit an enemy
	 */
	public Projectile(Pixmap pixmap, Location3D loc, Dimension size,
			int playerID, int damage, int health) {
		super(pixmap, loc, size, playerID, health);
		myDamage = damage;
		final Projectile toDestroy = this;
		myTimer = new DelayedTask(getHealth(), new Runnable() {
			Projectile p = toDestroy;

			public void run() {
				p.die();
			}
		});
	}

	/**
	 * Sets the enemy that the pojectile is attempting to hit.
	 * 
	 * @param enemy
	 *            is the enemy that is being targeted
	 */
	public void setEnemy(InteractiveEntity enemy) {
		myTarget = enemy;
	}

	/**
	 * Returns the damage done by the projectile.
	 * 
	 * @return the amount of damage done by the projectile.
	 */
	public int getDamage() {
		return myDamage;
	}

	/**
	 * Increases the amount of damage that a projectile does.
	 * 
	 * @param damage
	 *            is the amount of additional damage a projectile will do
	 */
	public void addDamage(int damage) {
		myDamage += damage;
	}

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		this.move(myTarget.getWorldLocation());
		myTimer.update(elapsedTime);
		if (this.intersects(myTarget.getWorldLocation())) {
			attack(myTarget);
			this.die();
		}

	}

	/**
	 * This method is called when the projectile hits its target and it inflicts
	 * damage on its target.
	 * 
	 * @param interactiveEntity
	 *            is the target of the projectile
	 */
	public void attack(InteractiveEntity interactiveEntity) {
		interactiveEntity.changeHealth(myDamage);
	}

	@Override
	public int getSpeed() {
		return DEFAULT_PROJECTILE_SPEED;
	}

	/**
	 * Makes a clone of the projectile.
	 * 
	 * @param other
	 *            is another projectile
	 * @param shootFrom
	 *            is the location that the projectile is being shot from
	 * @return a new clone of the projectile
	 */
	public Projectile copy(Projectile other, Location3D shootFrom) {
		return new Projectile(new Pixmap(other.getImage()), new Location3D(
				shootFrom), new Dimension(other.getSize()),
				other.getPlayerID(), other.getDamage(), other.getHealth());
	}
}