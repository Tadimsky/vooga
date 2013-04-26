package games.scroller.marioGame.spritesDefinitions.collisions;

import util.Vector;
import vooga.scroller.collision_manager.CollisionDirection;
import vooga.scroller.marioGame.spritesDefinitions.players.Mario;
import vooga.scroller.sprites.Sprite;
import vooga.scroller.sprites.interfaces.ICollectible;
import vooga.scroller.sprites.interfaces.IDoor;
import vooga.scroller.sprites.interfaces.IEnemy;
import vooga.scroller.sprites.interfaces.IPlatform;
import vooga.scroller.sprites.superclasses.Player;
import vooga.scroller.util.Direction;


/**
 * This class is specific to our game and is not intended to be a part of the
 * framework. It is meant to handle certain collisions specific to our Mario
 * game. If the game designer wants to implement specific collision helper methods
 * this is the place where those methods should go. <br>
 * <br>
 * Ultimately, this is the place where the game designer should dump all collision
 * logic into. He/She can then call these methods because VisitMethods.java
 * has a instance of this class.
 * 
 * @author Jay Wang
 */
public class MarioCollisions {

    private static final double FRICTION = .5;
    private CollisionDirection direction = new CollisionDirection();

    protected void marioAndLevelPortalCollision (Player player, IDoor levelPortal) {
        levelPortal.goToNextLevel(player);
    }

    protected void marioAndEnemyCollision (Mario player, IEnemy enemy) {
        if (direction.collisionDirection(player, enemy).equals(Direction.TOP)) {
            enemy.takeHit(player.getHit());
        }
        else {
            player.takeHit(enemy.getHit());
        }
    }

    protected void marioAndCollectibleCollision (Mario player, ICollectible collectible) {
        player.incrementScore(collectible.getValue());
        collectible.takeHit(player.getHit());
    }

    protected void marioAndPlatformCollision (Mario player, IPlatform platform) {

        Direction collisionType = direction.collisionDirection(player, platform);

        if (collisionType == null) return;

        switch (collisionType) {
            case TOP:
                player.setCenter(player.getX(), platform.getTop() - (player.getHeight() / 2));
                Vector v = player.getVelocity().getComponentVector((double) Sprite.DOWN_DIRECTION);
                v.negate();
                player.addVector(v);

                Vector right = player.getVelocity().getComponentVector(Sprite.RIGHT_DIRECTION);
                Vector left = player.getVelocity().getComponentVector(Sprite.LEFT_DIRECTION);

                right.negate();
                right.scale(FRICTION);
                left.negate();
                left.scale(FRICTION);
                player.addVector(right);
                player.addVector(left);

                Vector sLeft = platform.getVelocity().getComponentVector(Sprite.LEFT_DIRECTION);
                sLeft.scale(FRICTION);
                Vector sRight = platform.getVelocity().getComponentVector(Sprite.RIGHT_DIRECTION);
                sRight.scale(FRICTION);

                player.addVector(sRight);
                player.addVector(sLeft);

                break;
            case BOTTOM:
                player.setCenter(player.getX(), platform.getBottom() + (player.getHeight() / 2));

                Vector up = player.getVelocity().getComponentVector(Sprite.UP_DIRECTION);
                up.negate();
                player.addVector(up);

                break;
            case LEFT:
                player.setCenter(platform.getLeft() - (player.getWidth() / 2), player.getY());
                Vector l = player.getVelocity().getComponentVector(Sprite.LEFT_DIRECTION);
                l.negate();
                player.addVector(l);

                break;
            case RIGHT:
                player.setCenter(platform.getRight() + (player.getWidth() / 2), player.getY());

                Vector r = player.getVelocity().getComponentVector(Sprite.RIGHT_DIRECTION);
                r.negate();
                player.addVector(r);

                break;
            default:
                break;
        }
    }

}
