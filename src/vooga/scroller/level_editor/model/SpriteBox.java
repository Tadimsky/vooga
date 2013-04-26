
package vooga.scroller.level_editor.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;
import vooga.scroller.level_editor.controllerSuite.LEGrid;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.util.Renderable;
import vooga.scroller.view.IPaintable;


public class SpriteBox implements IPaintable {

    private boolean isAvailable;
    private Rectangle myBounds;
    private Set<SpriteBox> myCombinedBoxes;
    private LEGrid myParent;
    private int mySize;
    private Sprite mySprite;

    public SpriteBox (LEGrid parent, int x, int y) {
        myParent = parent;
        mySize = myParent.getSpriteSize();
        setBounds(x, y);
        setAvailable();
        myCombinedBoxes = new HashSet<SpriteBox>();
    }

    public void addSprite (Sprite spr) {
        mySprite = spr;
        mySprite.setCenter(myBounds.getX() + mySprite.getWidth() / 2,
                           myBounds.getY() + mySprite.getHeight() / 2);
        setUnavailable();
    }

    public void combineWith (SpriteBox nearestBox) {
        myCombinedBoxes.add(nearestBox);
        nearestBox.myCombinedBoxes.add(this);
        nearestBox.setUnavailable();
    }

    public void deleteSprite () {
        mySprite = null;
        setAvailable();
        for (SpriteBox box : myCombinedBoxes) {
            box.myCombinedBoxes.remove(this);
            box.deleteSprite();
        }
        myCombinedBoxes = new HashSet<SpriteBox>();
    }

    public Rectangle getBounds () {
        return myBounds;
    }


    public Sprite getSprite () {
        return mySprite;
    }

    public int getX () {
        return myBounds.x;
    }

    public int getY () {
        return myBounds.y;
    }

    public boolean isAvailable () {
        return isAvailable;
    }

    @Override
    public void paint (Graphics2D pen) {
        if (mySprite != null) {
            mySprite.paint(pen);
        }
        pen.setColor(Color.BLACK);
        pen.drawRect(getX(), getY(), myBounds.width, myBounds.height);
    }

    private void setAvailable () {
        isAvailable = true;
    }

    private void setBounds (int x, int y) {
        myBounds = new Rectangle(x * mySize, y * mySize, mySize, mySize);
    }

    private void setUnavailable () {
        isAvailable = false;
    }

}
