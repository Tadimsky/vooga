package vooga.towerdefense.model.AStar;

import java.awt.Point;
import java.util.ArrayList;

public class PathFinder {

        AreaMap map;
       
        public ArrayList<Point> getWaypoints(AreaMap map) {
                this.map = map;
               
                System.out.println("vooga.towerdefense.model.AStar Heuristic initializing...");
                AStarHeuristic heuristic = new DiagonalHeuristic();
               
                System.out.println("vooga.towerdefense.model.AStar initializing...");
                AStar aStar = new AStar(map, heuristic);
               
                System.out.println("Calculating shortest path with vooga.towerdefense.model.AStar...");
                ArrayList<Point> shortestPath = aStar.calcShortestPath(map.getStartLocationX(), map.getStartLocationY(), map.getGoalLocationX(), map.getGoalLocationY());
               
                //System.out.println("Printing map of shortest path...");
                //new PrintMap(map, shortestPath);
               
                System.out.println("Calculating optimized waypoints...");
                ArrayList<Point> waypoints = calculateWayPoints(shortestPath);
               
                return waypoints;
        }
       
        private ArrayList<Point> calculateWayPoints(ArrayList<Point> shortestPath) {
                ArrayList<Point> waypoints = new ArrayList<Point>();
               
                shortestPath.add(0,map.getStartNode().getPoint());
                shortestPath.add(map.getGoalNode().getPoint());
               
                Point p1 = shortestPath.get(0);
                int p1Number = 0;
                waypoints.add(p1);
               
                Point p2 = shortestPath.get(1);
                int p2Number = 1;
               
                while(!p2.equals(shortestPath.get(shortestPath.size()-1))) {
                        if(lineClear(p1, p2)) {
                                //make p2 the next point in the path
                                p2Number++;
                                p2 = shortestPath.get(p2Number);
                        } else {
                                p1Number = p2Number-1;
                                p1 = shortestPath.get(p1Number);
                                waypoints.add(p1);
                                System.out.println("Got waypoint: " + p1.toString());
                                p2Number++;
                                p2 = shortestPath.get(p2Number);
                        }
                }
                waypoints.add(p2);
               
                return waypoints;
        }
       
        private boolean lineClear(Point a, Point b) {
                ArrayList<Point> pointsOnLine = BresenhamsLine.getPointsOnLine(a, b);
                for(Point p : pointsOnLine) {
                        if(map.getNode(p.x, p.y).isObstacle) {
                                return false;
                        }
                }
                return true;
        }
}

