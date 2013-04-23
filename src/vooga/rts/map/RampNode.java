package vooga.rts.map;

/**
 * Ramp nodes connect to every node touching them. This will be used for more
 * advanced maps. The fact that its a ramp node is specified by its height of -1
 * 
 * @author Challen Herzberg-Brovold
 * 
 */
public class RampNode extends Node {

    public RampNode (int x, int y) {
        super(x, y, -1);
    }

    /**
     * Connects to any node it is touching.
     */
    @Override
    public boolean connectsTo (Node other) {
        return true;
    }
}
