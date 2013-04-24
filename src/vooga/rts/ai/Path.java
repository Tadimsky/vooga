package vooga.rts.ai;


import java.util.LinkedList;
import java.util.Queue;
import vooga.rts.map.*;
import vooga.rts.util.Location;
import vooga.rts.util.Location3D;

/**
 * Stores the queue of nodes that the unit needs to traverse to get to its 
 * desired location. For more complicated pathfinding algorithms, the path might
 * include an update which changes it based on new information.
 *
 * 
 * @author Challen Herzberg-Brovold
 *
 */ 
public class Path {
    
    private Queue<Node> myPath;
    
    public Path (Queue<Node> path) {
        myPath = path;
    }
    
    public Path () {
        this(new LinkedList<Node>());
    }
    /**
     * 
     * @return This methods will return the next node to go
     */
    public Location3D getNext() { 
        return myPath.poll().getCenter();
    }
    
    /**
     * 
     * @return the number of nodes in the queue.
     */
    public int size () {
        return myPath.size();
    }
    
}
