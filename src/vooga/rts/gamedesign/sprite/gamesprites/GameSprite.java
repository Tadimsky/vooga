package vooga.rts.gamedesign.sprite.gamesprites;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Observable;
import vooga.rts.IGameLoop;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

public abstract class GameSprite extends Observable implements IGameLoop {

	// private ThreeDimension mySize;
	private Dimension mySize;
	// store myWorldLocation

	// private ThreeDimension myOriginalSize;
	private Dimension myOriginalSize;

	private Location3D myWorldLocation;
	private Point2D myScreenLocation;
	private Rectangle myWorldBounds;

	private Pixmap myPixmap;

	private boolean myVisible;

	public GameSprite(Pixmap image, Location3D center, Dimension size) {
		myPixmap = new Pixmap(image);
		mySize = new Dimension(size);
		myOriginalSize = mySize;
		myWorldLocation = new Location3D(center);
		resetBounds();
		myVisible = true;
	}

	/**
	 * Returns shape's left-most coordinate in pixels.
	 */
	public double getLeft() {
		return myWorldLocation.getX() - mySize.width / 2;
	}

	/**
	 * Returns shape's up-most coordinate in pixels.
	 */
	public double getUp() {
		return myWorldLocation.getY() - mySize.height / 2;
	}

	/**
	 * Returns shape's right-most coordinate in pixels.
	 */
	public double getRight() {
		return myWorldLocation.getX() + mySize.width / 2;
	}

	/**
	 * Returns shape's bottom-most coordinate in pixels.
	 */
	public double getBottom() {
		return myWorldLocation.getY() + mySize.height / 2;
	}

	/**
	 * Returns shape's x-axis length in pixels.
	 */
	public double getWidth() {
		return mySize.getWidth();
	}

	/**
	 * Returns shape's y-axis length in pixels.
	 */
	public double getDepth() {
		return mySize.getHeight();
	}

	/**
	 * Return shape's z-axis length in pixels. need to implement!
	 */
	public double getHeight() {
		return mySize.getHeight();
	}

	/**
	 * Resets shape's size.
	 */
	public void setSize(int width, int height) {
		mySize.setSize(width, height);
		resetBounds();
	}

	/**
	 * Returns shape's size.
	 */
	public Dimension getSize() {
		return mySize;
	}

	/**
	 * Scales shape's size by the given factors.
	 */
	public void scale(double widthFactor, double heightFactor) {
		mySize.setSize(mySize.width * widthFactor, mySize.height * heightFactor);
		resetBounds();
	}

	public void reset() {
		mySize = myOriginalSize;
	}

	public void paint(Graphics2D pen) {
		if (!isVisible())
			return;

		if (Camera.instance().issVisible(getWorldLocation())) {
			myScreenLocation = Camera.instance().worldToView(myWorldLocation);
			// if (Camera.instance().isVisible(myScreenLocation)) {
			myPixmap.paint(pen, myScreenLocation, mySize);
			// }
		}
	}

	/**
	 * Returns whether a Game Sprite is visible or not.
	 * 
	 * @return
	 */
	private boolean isVisible() {
		return myVisible;
	}

	/**
	 * Sets the visibility of the sprite.
	 * 
	 * @param visible
	 *            Whether the sprite is visible.
	 */
	public void setVisible(boolean visible) {
		myVisible = visible;
	}

	/**
	 * Returns rectangle that encloses this shape.
	 */
	protected void resetBounds() {
		setWorldBounds();
	}

	/**
	 * Returns the world location of the GameSprite.
	 * 
	 * @return the current world location of the GameSprite
	 */
	public Location3D getWorldLocation() {
		return myWorldLocation;
	}

	/**
	 * Sets the bounds of the world location.
	 */
	public void setWorldBounds() {
		myWorldBounds = new Rectangle((int) getLeft(), (int) getUp(),
				getSize().width, getSize().height);

	}

	/**
	 * Returns whether or not the GameSprite intersects another Location3D.
	 * 
	 * @param other
	 *            is the Location3D that we are seeing if we intersect with
	 * @return true if the GameSprite intersects the Location3D and false if the
	 *         GameSprite does not intersect the Location3D
	 */
	public boolean intersects(Location3D other) {
		return myWorldBounds.contains(other.to2D());
	}

	/**
	 * Returns whether or not the GameSprite intersects a certain rectangle.
	 * 
	 * @param other
	 *            is the rectangle who we are seeing if we intersect with
	 * @return true if the GameSprite intersects the rectangle and false if the
	 *         GameSprite does not intersect the rectangle.
	 */
	public boolean intersects(Rectangle other) {
		return myWorldBounds.intersects(other);
	}

	/**
	 * Returns the bounds of the world location.
	 * 
	 * @return the bounds of the world location
	 */
	public Rectangle getBounds() {
		// System.out.println("Bounds =" + myWorldBounds);
		return myWorldBounds;
	}

	/**
	 * Sets the world location.
	 * 
	 * @param x
	 *            is the x position of the world location
	 * @param y
	 *            is the y position of the world location
	 * @param z
	 *            is the z position of the world location
	 */
	public void setWorldLocation(double x, double y, double z) {
		myWorldLocation.setLocation(x, y, z);
	}

	public void setWorldLocation(Location3D togo) {
		myWorldLocation = new Location3D(togo);
	}

	public void update(double elapsedTime) {

	}

	/**
	 * Returns the image of the sprite.
	 * 
	 * @return the image of the sprite
	 */
	public Pixmap getImage() {
		return myPixmap;
	}
}
