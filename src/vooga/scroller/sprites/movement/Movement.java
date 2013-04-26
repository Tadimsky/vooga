package vooga.scroller.sprites.movement;


/**
 * UPDATE - Movement has turned into an interface that enforces on method - execute(). 
 * Execute will execute a movement on the sprite. 
 * <br>
 * <br>
 * Interface using the strategy design pattern. Every movement will implement Movement. 
 * <br>
 * <br>
 * If you want to create a new Movement type, implement this interface and implement an 
 * execute() method for that specific Movement subclass. 
 * 
 * @author Jay Wang
 * @author Scott Valentine
 *
 */
public interface Movement {
    
    /**
     * Executes this movement.
     */
    public void execute();

}
