package vooga.rts.ai;


import java.util.Queue;
import vooga.rts.map.*;
import vooga.rts.util.Location;

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
    private Node myNext;
    
    public Path (Queue<Node> path) {
        myPath = path;
        setNext();
    }
    
    /**
     * 
     * @return This methods will return the next node to go
     */
    public Node getNext() { 
        return myNext;
    }
    
    public void setNext() {
        myNext = myPath.poll();
    }
    /**
     * 
     * @return the number of nodes in the queue.
     */
    public int size () {
        return myPath.size();
    }
    
}
