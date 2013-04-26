package vooga.fighter.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import util.Location;

public class ShapeMeasurements {
	
	 /**
	  * A class that returns basic measurements of 
	  * Shapes
	 */

	public ShapeMeasurements() {
	}
	
	 /**
	  * Uses rectange bounds of each shape to get a quick 
	  * Point2D for the center.
	 */
	public static Point2D getQuickCenter(Shape shape1){
		Rectangle2D rect = shape1.getBounds2D();
		return new Location(rect.getCenterX(), rect.getCenterY());
	}
	 /**
	  * Uses path of bounds to precisely calculate the centroid of 
	  * the shape.  DISCLAMER: if the shape has holes (like a donut
	  * this method will find the center to be inside the donut, 
	  * so use wisely!
	 */
	public static Location getPrecisionCenter(Shape shape1, double rotationAngle){
		AffineTransform affinetransform = new AffineTransform().getRotateInstance(Math.toRadians(rotationAngle));
		PathIterator path1 = shape1.getPathIterator(affinetransform, .001);
		float sumX = 0;
		float sumY = 0;
		int xcount = 0;
		int ycount = 0;
		float[] coordinates = new float[6];
		float[] pastcoordinates = new float[6];
;		while(!path1.isDone()){
			System.out.print(xcount);
			path1.currentSegment(coordinates);
			if(coordinates[0]!=pastcoordinates[0]){ 
				//Unfortunate, but makes this work for rectanges so I will keep the if statement until I refactor
				pastcoordinates[0] = coordinates[0];
				sumX += coordinates[0];
				xcount++;
				
			}
			if(coordinates[1]!=pastcoordinates[1]){
				pastcoordinates[1] = coordinates[1];
				sumY += coordinates[1];
				ycount++;
			}
			path1.next();
		}
		return new Location(sumX/xcount, sumY/ycount);
	}
	
	
	
	 /**
	  * Uses path of bounds to precisely calculate the centroid of 
	  * the shape.  DISCLAMER: if the shape has holes (like a donut
	  * this method will find the center to be inside the donut, 
	  * so use wisely! no rotation considered
	 */
	public static Location getPrecisionCenter(Shape shape1){
		return getPrecisionCenter(shape1,0);
	}
	
	 /**
	  * Treating the shape as a rectangle, returns 
	  *  the location of the Bottom Right Corner
	 */
	public static Location getBottomRightCorner(Shape shape1){
		Rectangle2D rect = shape1.getBounds2D();
		return new Location(rect.getMaxX(),rect.getMaxY());
	}
	
	 /**
	  * Treating the shape as a rectangle, returns the location of
	  * the bottom left corner
	 */
	public static Location getBottomLeftCorner(Shape shape1){
		Rectangle2D rect = shape1.getBounds2D();
		return new Location(rect.getMinX(),rect.getMaxY());
	}
	
	 /**
	  * Treating the shape as a rectangle, returns the location of
	  * the top right Corner
	 */
	public static Location getTopRightCorner(Shape shape1){
		Rectangle2D rect = shape1.getBounds2D();
		return new Location(rect.getMaxX(),rect.getMinY());
	}
	
	 /**
	  * Treating the shape as a rectangle, returns the location of
	  * the top left corner
	 */
	public static Location getTopLeftCorner(Shape shape1){
		Rectangle2D rect = shape1.getBounds2D();
		return new Location(rect.getMinX(),rect.getMinY());
	}


}
