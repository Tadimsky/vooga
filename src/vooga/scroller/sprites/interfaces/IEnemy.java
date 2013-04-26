package vooga.scroller.sprites.interfaces;


/**
 * This interface should be implemented by any Sprites that are enemies. If you 
 * are an enemy, you collision with the player should be pretty standard. Depending 
 * on who is attacking who (which is oftentimes based on the Collision direction), 
 * either the player takes a hit of the enemy's hit damage or the enemy takes a hit 
 * of the player's hit damage. 
 * 
 * @author Jay Wang
 */
public interface IEnemy extends Locatable {
    
    /**
     * This is going to decrement the enemy's health by damage. An enemy 
     * should take a hit if the player has attacked the enemy. Whether that 
     * happens would be based on the direction of the collision. 
     * @param damage
     */
    public void takeHit(int damage);
    
    /** 
     * This returns the amount of damage an enemy would do to the player 
     * in the event that the enemy is attacking the player. Once again, that 
     * would be dependent on the direction of the collision. 
     * @return int value representing enemy's hit damage
     */
    public int getHit();

}
