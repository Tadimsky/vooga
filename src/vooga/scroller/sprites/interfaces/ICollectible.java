package vooga.scroller.sprites.interfaces;

/**
 * This interface should be implemented by any Sprites that are collectible by the player. 
 * The canonical example we have for a Collectible object is a Coin. Another example would 
 * be something like a powerup. Note, that if you want to implement more interesting Collectibles 
 * you would need to add whatever methods needed to handle that collision here. 
 * 
 * @author Jay Wang
 */
public interface ICollectible extends Locatable {
     
    /**
     * This returns the value of the Collectible. For a Coin, it would be 
     * the number of points a player gains by collecting it. 
     * @return int that represents the value of the Collectible 
     */
    public int getValue();
    
    /** 
     * When a player and collectible collide, the Coin takes a hit. This method 
     * will decrement the Collectible's health by the parameter hitValue. The point 
     * of this method is to essentially "kill" the Collectible. That is how a Collectible 
     * gets removed from the list of active sprites. 
     * @param hitValue
     */
    public void takeHit(int hitValue);
    
    /** 
     * This returns the health points of a Collectible. We would imagine most 
     * Collectibles to have a health of 1. Once they collide with a player, the 
     * Collectible should take a hit, sending the health to be less than or equal 
     * to 0 and that will effectively remove the Collectible from the game. 
     * @return int that represents the Collectible's health
     */
    public int getHealth();

}
