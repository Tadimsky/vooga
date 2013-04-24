package vooga.rts.ai;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import vooga.rts.map.GameMap;
import vooga.rts.map.Node;
import vooga.rts.map.NodeMap;

/**
 * An implementation of the pathfinder interface that uses the A-star algorithm 
 * to create a path for units based on the Nodes in our map. 
 * 
 * @author Challen Herzberg-Brovold, Jonno Schmidt
 *
 */
public class AstarFinder implements PathFinder {
    
    @Override
    public Path calculatePath (Node start, Node finish, NodeMap map) {
        Path result = null;
        List<Node> close = new ArrayList<Node>();
        List<Node> open = new ArrayList<Node>();
        open.add(start);
        Map<Node, Node> comesFrom = new HashMap<Node, Node>();
        Map<Node, Double> gScore = new HashMap<Node, Double>();
        Map<Node, Double> fScore = new HashMap<Node, Double>();
        gScore.put(start, 0.0);
        fScore.put(start, calculateHeuristic(start, finish));
        double fMax = 0;
        double gMax = 0;
        while (open.size() > 0) {
            Node current = getLowest(fScore, open);
            if (current.equals(finish)) {
                System.out.println("Finished finding");
                result  =  new Path(constructPath(comesFrom, finish));
                break;
            }
            open.remove(current);
            close.add(current);
            for (Node neighbor: map.getNeighbors(current)) {
                if (neighbor == null || !neighbor.connectsTo(current)) {
                    continue;
                }
                double newGscore = gScore.get(current) + Node.NODE_SIZE;
                if (close.contains(neighbor) && newGscore >= gScore.get(neighbor)) {
                    continue;
                }
                if (!open.contains(neighbor) || newGscore < gScore.get(neighbor)) {
                    comesFrom.put(neighbor, current);
                    gScore.put(neighbor, newGscore);
                    fScore.put(neighbor, gScore.get(neighbor) +
                               calculateHeuristic(neighbor, finish));
                    if (!open.contains(neighbor)) {
                        if (!(neighbor.getTier() > 0)) {
                            open.add(neighbor);
                        }
                    }
                }
            }
        }       
        return result; //At some point, there will be a catch so it just returns to the closest node to the desired one.
    }

    /**
     * Calculates the heuristic that is used by the A* finder. 
     * Currently, the heuristic we use is the Manhattan difference, which is 
     * the sum of the change in x and the change in y
     * 
     * @param start the starting node from which to calculate the heuristic
     * @param finish the ending node from which to calculate the heuristic
     * @return the heuristic value.
     */
    private double calculateHeuristic (Node start, Node finish) {
        int dx = Math.abs(finish.getX() - start.getX());
        int dy = Math.abs(finish.getY() - start.getY());
        return dx + dy;
    }

    /**
     * Constructs the path the finish node to the beginning node, using the 
     * comesFrom map. 
     * @param comesFrom maps each node to the node connected to it which has the lowest heuristic value
     * @param finish the destination node, i.e. where the unit wants to go.
     * @return a queue of nodes from finish to the starting point 
     */
    private Queue<Node> constructPath(Map<Node, Node> comesFrom, Node finish) {
        Queue<Node> path = new LinkedList<Node>();
        if (comesFrom.containsKey(finish)) {
            path.addAll(constructPath(comesFrom, comesFrom.get(finish)));
            path.add(finish);
        }
        else {
            path.add(finish);
        }
        return path;
    }
    
    /**
     * Returns the node with the lowest f-value (node's heuristic value plus its
     * G-score).
     * 
     * @param fScore map of nodes and their f-scores.
     * @param from list of open nodes still to be explored
     * @return the node with the lowest value.
     */
    private Node getLowest(Map<Node, Double> fScore, List<Node> from) {
        Node minNode = from.get(0);
        double minF = fScore.get(minNode);
        for (int i = 1; i < from.size(); i++) {
            Node current = from.get(i);
            double f = fScore.get(current);
            if (f < minF) {
                minNode = current;
                minF = f;
            }
        }
        return minNode;
    }
   
}

