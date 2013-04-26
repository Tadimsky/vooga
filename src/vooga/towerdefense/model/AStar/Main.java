package vooga.towerdefense.model.AStar;

import java.awt.Point;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// public AStar(AreaMap map, AStarHeuristic heuristic)

		int[][] obstacleMap = { 
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
				{1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
				{1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
				{1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
				{1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1 },
				{1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 }};
		
		int obstacleMap2[][] = { 
				{ 0, 1, 0, 0, 0 }, 
				{ 0, 1, 0, 1, 0 },
				{ 0, 1, 0, 1, 0 }, 
				{ 0, 1, 0, 1, 0 }, 
				{ 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 1, 0 }, 
				{ 0, 0, 0, 1, 0 } };

		// int mapWith, int mapHeight, int[][] obstacleMap
		AreaMap map = new AreaMap(obstacleMap[0].length, obstacleMap.length,
				obstacleMap);
		AStarHeuristic heuristic = new DiagonalHeuristic();

		AStar aStar = new AStar(map, heuristic);
		// aStar.calcShortestPath(int startX, int startY, int goalX, int goalY)
		ArrayList<Point> points = aStar.calcShortestPath(5, 0, 5, 15);

		for (Point p : points) {
			System.out.print(p.getX() + " " + p.getY() + ", ");
		}
		removeLinearPoints(points);
		System.out.println("\nreduced Path");
		for (Point p : points) {
			System.out.print(p.getX() + " " + p.getY() + ", ");
		}

	}

	private static void removeLinearPoints(ArrayList<Point> points) {
		for (int i = 0; i < points.size() - 2; i++) {
			if (points.get(i).getX() == points.get(i + 1).getX()
					&& points.get(i + 1).getX() == points.get(i + 2).getX()
					|| points.get(i).getY() == points.get(i + 1).getY()
					&& points.get(i + 1).getY() == points.get(i + 2).getY()) {
				points.remove(i + 1);
				i--;
			}
		}
	}

}
