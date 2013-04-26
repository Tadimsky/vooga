package vooga.towerdefense.gameElements;

import java.awt.Graphics2D;
import java.util.List;


/**
 * manages animations of a gameElement, need to be subclassed
 * 
 * @author Zhen Gou
 * 
 */

public abstract class StateManager {
    private GameElement myGameElement;

    public StateManager (GameElement unit) {
        myGameElement = unit;
    }

    public abstract void updateAndPaint (Graphics2D pen);

    public GameElement getGameElement () {
        return myGameElement;
    }

}
