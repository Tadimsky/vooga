package vooga.scroller.marioGame.spritesDefinitions.collisions;


import vooga.scroller.marioGame.spritesDefinitions.players.Mario;
import vooga.scroller.sprites.interfaces.ICollectible;
import vooga.scroller.sprites.interfaces.IDoor;
import vooga.scroller.sprites.interfaces.IEnemy;
import vooga.scroller.sprites.interfaces.IPlatform;
import vooga.scroller.sprites.superclasses.Player;

/**
 * This is where you want to place your visit methods. To keep this class as 
 * clean as possible, I don't actually handle the collision logic here. I handle 
 * that logic in a Game specific Collisions class. You can, of course, handle all 
 * collisions in these visit() methods if you prefer. 
 * 
 * @author Jay Wang
 *
 */
public class VisitMethods {

       
    private MarioCollisions collisions = new MarioCollisions();

    public void visit (Mario mario, IPlatform platform) {
        collisions.marioAndPlatformCollision(mario, platform);
    }
    
    public void visit (Mario mario, ICollectible collectible) {
        collisions.marioAndCollectibleCollision(mario, collectible);
    }
        
    public void visit (Mario mario, IEnemy enemy) {
        collisions.marioAndEnemyCollision(mario, enemy);
    }
    
    public void visit (Player player, IDoor levelPortal) {
        collisions.marioAndLevelPortalCollision(player, levelPortal);
    }
}
