package vooga.scroller.level_management;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import vooga.scroller.collision_manager.CollisionManager;
import vooga.scroller.level_editor.Level;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.superclasses.GameCharacter;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.Direction;
import vooga.scroller.view.GameView;


/**
 * Manages all sprites that appear in the level.
 * 
 * @author Scott Valentine
 * 
 */
public class SpriteManager {

    private List<Sprite> mySprites;
    private List<Sprite> myFrameOfActionSprites;
    private List<Sprite> myFrameOfReferenceSprites;
    private Player myPlayer;
    private Dimension myFramceOfReferenceSize;
    private Level myLevel;

    /**
     * Constructs a new SpriteManager given the level in which all the sprites of this manager
     * are active.
     * 
     * @param level is the level for which this manages sprites.
     */
    public SpriteManager (Level level) {
        myLevel = level;
        mySprites = new ArrayList<Sprite>();
        initFrames();
    }

    private void initFrames () {
        myFrameOfActionSprites = new ArrayList<Sprite>();
        myFrameOfReferenceSprites = new ArrayList<Sprite>();
    }

    /**
     * Removes a given sprite from the manager.
     * 
     * @param sprite to be removed.
     */
    public void removeSprite (Sprite sprite) {
        mySprites.remove(sprite);
    }

    /**
     * Adds a given sprite to the manager
     * 
     * @param sprite to be added.
     */
    public void addSprite (Sprite sprite) {
        mySprites.add(sprite);
    }

    /**
     * Adds a player to this sprite manager.
     * 
     * @param player to be added.
     */
    public void addPlayer (Player player) {
        myPlayer = player;
        myPlayer.setCenter(myLevel.getStartPoint().x, myLevel.getStartPoint().y);
        for (Sprite sprite : mySprites) {
            if (sprite instanceof GameCharacter) {
                ((GameCharacter) sprite).addTarget(player);
            }
        }
    }

    /**
     * Gives the current player in this sprite manager
     * 
     * @return the player managed by this.
     */
    public Player getPlayer () {
        return myPlayer;
    }

    /**
     * Updates all the sprites in the sprite manager.
     * 
     * @param elapsedTime is the time passed since the last update
     * @param bounds is the bounds of the current view.
     * @param gameView is the view used by the game.
     */
    public void updateSprites (double elapsedTime, Dimension bounds, GameView gameView) {

        if (myPlayer != null) {
            updateFrames(gameView);
            myPlayer.update(elapsedTime, bounds);
            checkPlayerOutOfBounds();
            if (myPlayer.getHealth() <= 0) {
                myPlayer.handleDeath();
            }
            for (Sprite s : myFrameOfActionSprites) {
                s.update(elapsedTime, bounds);
                if (s instanceof GameCharacter) {
                    if (((GameCharacter) s).getHealth() <= 0) {
                        this.removeSprite(s);
                    }
                }
            }
            if (myPlayer.getHealth() <= 0) {
                myPlayer.handleDeath();
            }
            intersectingSprites();
        }
    }

    private void checkPlayerOutOfBounds () {
        double xCoord = myPlayer.getX();
        double yCoord = myPlayer.getY();
        double rightLevelBounds = myLevel.getLevelBounds().getWidth();
        double leftLevelBounds = 0;
        double upperLevelBounds = 0;
        double lowerLevelBounds = myLevel.getLevelBounds().getHeight();
        rightLevelBounds =
                myLevel.getScrollManager().getHardBoundary(Direction.RIGHT, rightLevelBounds);
        lowerLevelBounds =
                myLevel.getScrollManager().getHardBoundary(Direction.BOTTOM, lowerLevelBounds);
        leftLevelBounds =
                myLevel.getScrollManager().getHardBoundary(Direction.LEFT, leftLevelBounds);
        upperLevelBounds =
                myLevel.getScrollManager().getHardBoundary(Direction.TOP, upperLevelBounds);

        if (xCoord >= rightLevelBounds) {
            xCoord = rightLevelBounds - (myPlayer.getSize().getWidth() / 2);
            myPlayer.setCenter(xCoord, yCoord);
        }
        if (xCoord <= leftLevelBounds) {
            xCoord = leftLevelBounds + (myPlayer.getSize().getWidth() / 2);
            myPlayer.setCenter(xCoord, yCoord);
        }
        if (yCoord <= upperLevelBounds) {
            yCoord = upperLevelBounds + (myPlayer.getSize().getHeight() / 2);
            myPlayer.setCenter(xCoord, yCoord);
        }
        if (yCoord >= lowerLevelBounds) {
            yCoord = upperLevelBounds - (myPlayer.getSize().getHeight() / 2);
            myPlayer.setCenter(xCoord, yCoord);
        }
    }

    private void updateFrames (GameView gameView) {
        myFrameOfActionSprites.clear();
        myFrameOfReferenceSprites.clear();
        myFramceOfReferenceSize = gameView.getSize();
        if (mySprites.size() > 0) {
            for (Sprite s : mySprites) {
                updateFrameOfActionSprites(s, myFramceOfReferenceSize);
            }
        }
    }

    private void intersectingSprites () {
        Sprite obj1;
        Sprite obj2;
        CollisionManager cm = new CollisionManager(myLevel);

        mySprites.add(myPlayer);
        for (int i = 0; i < mySprites.size(); i++) {
            for (int j = 0; j < mySprites.size(); j++) {
                obj1 = mySprites.get(i);
                obj2 = mySprites.get(j);
                if (obj1.intersects(obj2)) {
                    cm.handleCollision(obj1, obj2);
                }
            }
        }
        mySprites.remove(mySprites.size() - 1);
    }

    private void updateFrameOfActionSprites (Sprite sprite, Dimension frame) {
        boolean condition = myPlayer != null &&
                             myLevel.getLeftBoundary(frame) <= sprite.getX()
                             && myLevel.getRightBoundary(frame) >= sprite.getX()
                             && myLevel.getLowerBoundary(frame) >= sprite.getY()
                             && myLevel.getUpperBoundary(frame) < +sprite.getY();
        if (!myFrameOfActionSprites.contains(sprite) && condition) {
            myFrameOfActionSprites.add(sprite);
        }
    }

    /**
     * Paints all of the sprites in this manager.
     * 
     * @param pen is the graphics 2d which paints everything.
     */
    public void paint (Graphics2D pen) {
        if (myPlayer != null) {
            for (Sprite s : this.mySprites) {
                s.paint(pen, myPlayer.getCenter(), myPlayer.getPaintLocation());
            }
            myPlayer.paint(pen);
        }
    }
}
