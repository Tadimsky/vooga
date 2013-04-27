package vooga.rts.gamedesign.sprite.gamesprites;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Observable;
import vooga.rts.IGameLoop;
import vooga.rts.util.Camera;
import vooga.rts.util.Dimension3D;
import vooga.rts.util.IsometricConverter;
import vooga.rts.util.Location3D;
import vooga.rts.util.Pixmap;

public abstract class GameSprite extends Observable implements IGameLoop {

	// private ThreeDimension mySize;
	private Dimension mySize;
	private Dimension myOriginalSize;
	// store myWorldLocation

	// private ThreeDimension myOriginalSize;
	private Dimension3D myWorldDimension;

	private Location3D myWorldLocation;
	private Point2D myScreenLocation;
	private Rectangle myWorldBounds;
	private Rectangle myBase;
	private Pixmap myPixmap;

	private boolean myVisible;

	public GameSprite(Pixmap image, Location3D center, Dimension size) {
		myPixmap = new Pixmap(image);
		mySize = new Dimension(size);
		myOriginalSize = mySize;
		myVisible = true;
		myWorldLocation = new Location3D(center);
		myWorldDimension = IsometricConverter.toIsometric(mySize);
		setBase();
		resetBounds();
	}

	/**
	 * Returns shape's left-most coordinate in pixels.
	 */
	public double getLeft() {
		return Camera.instance().worldToView(myWorldLocation).getX()
				- myWorldDimension.getWidth() / 2;
	}

	/**
	 * Returns shape's up-most coordinate in pixels.
	 */
	public double getUp() {
		return Camera.instance().worldToView(myWorldLocation).getY()
				- myWorldDimension.getHeight() / 2;
	}

	/**
	 * Returns shape's right-most coordinate in pixels.
	 */
	public double getRight() {
		return Camera.instance().worldToView(myWorldLocation).getX()
				+ myWorldDimension.getWidth() / 2;
	}

	/**
	 * Returns shape's bottom-most coordinate in pixels.
	 */
	public double getBottom() {
		return Camera.instance().worldToView(myWorldLocation).getY();
	}

	/**
	 * Resets shape's size.
	 */
	public void setSize(int width, int height) {
		mySize.setSize(width, height);
		resetBounds();
	}
	/**
	 * 
	 */
	private void setBase() {

		Location3D temp = Camera.instance().deltaviewtoWorld(
				new Point2D.Double((myWorldDimension.getDepth() / 2), 0));
		temp.add(myWorldLocation);
		int height = (int)(Math.sqrt(Math.pow(myWorldDimension.getDepth()/2, 2) + Math.pow(myWorldDimension.getWidth()/2, 2)));

		myBase = new Rectangle((int)temp.getX(),(int) temp.getY(), height, height);
	}

	/**
	 * returns the base rectangle of the Sprite
	 * 
	 */
	public Rectangle getBase() {
		return myBase;
	}

	/**
	 * Returns shape's size.
	 */
	public Dimension getSize() {
		return mySize;
	}

	/**
	 * returns the 3D dimension of the Sprite.
	 * 
	 */
	public Dimension3D getWorldSize() {
		return myWorldDimension;
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
			Location3D paintLocation = IsometricConverter
					.calculatePaintLocation(myWorldLocation, myWorldDimension);
			myScreenLocation = Camera.instance().worldToView(paintLocation);
			// if (Camera.instance().isVisible(myScreenLocation)) {
			myPixmap.paint(pen, myScreenLocation, mySize);
			// }
		}
		// pen.draw(myWorldBounds);
	}

	/**
	 * Returns whether a Game Sprite is visible or not.
	 * 
	 * @return
	 */
	public boolean isVisible() {
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
	 * Returns the visibility of the sprite.
	 * 
	 * @return
	 */
	public boolean getVisible() {
		return myVisible;
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
		Point2D leftMostPoint = Camera.instance().worldToView(
				IsometricConverter.calculatePaintLocation(myWorldLocation,
						myWorldDimension));
		myWorldBounds = new Rectangle((int) leftMostPoint.getX(),
				(int) leftMostPoint.getY(), getSize().width, getSize().height);

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
		resetBounds();
	}

	public void setWorldLocation(Location3D togo) {
		setWorldLocation(togo.getX(), togo.getY(), togo.getZ());
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

	/**
	 * Sets the object to be in the changed state for the observer pattern.
	 */
	public void setChanged() {
		super.setChanged();
	}
}
