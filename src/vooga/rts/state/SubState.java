package vooga.rts.state;

import java.util.Observable;
import java.util.Observer;

/**
 * A state that is stored in the main state. Current examples of it include:
 * the Game, Menu and Loading state.
 * 
 * @author Challen Herzberg-Brovold
 *
 */
public abstract class SubState extends Observable implements State {
    
    /**
     * The substates need to be observable, and inform the mainstate (the observer)
     *  when they need to be switched.
     * @param observer
     */
    public SubState (Observer observer) {
        addObserver(observer);
    }
    
    public abstract void activate();
}
