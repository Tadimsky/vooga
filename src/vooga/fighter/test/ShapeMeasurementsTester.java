package vooga.fighter.test;

import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import org.junit.Test;

import vooga.fighter.util.ShapeMeasurements;

public class ShapeMeasurementsTester {

	public ShapeMeasurementsTester() {
	}
	
	@Test
	public void testgetQuickCenter() {
		Rectangle rect2 = new Rectangle(100,100);
		Ellipse2D ellipse = new Ellipse2D.Float(0,0, 100, 100);
		assertTrue(ShapeMeasurements.getQuickCenter(rect2).getX()==50.0);
		assertTrue(ShapeMeasurements.getQuickCenter(rect2).getY()==50.0);
		assertTrue(ShapeMeasurements.getQuickCenter(ellipse).getX()==50.0);
		assertTrue(ShapeMeasurements.getQuickCenter(ellipse).getY()==50.0);
	}
	
	@Test
	public void testgetPrecisionCenter() {
		Rectangle rect2 = new Rectangle(100,100);
		Ellipse2D ellipse = new Ellipse2D.Float(0,0, 100, 100);
		assertTrue(Math.abs(ShapeMeasurements.getPrecisionCenter(rect2).getX()-50.0)<.1);
		assertTrue(Math.abs(ShapeMeasurements.getPrecisionCenter(rect2).getY()-50.0)<.1);
		assertTrue(Math.abs(ShapeMeasurements.getPrecisionCenter(ellipse).getX()-50.0)<.1);
		assertTrue(Math.abs(ShapeMeasurements.getPrecisionCenter(ellipse).getY()-50.0)<.1);
	}
	
	@Test
	public void testgetPrecisionCenterWDirection() {
		Rectangle rect2 = new Rectangle(100,100);
		assertTrue(ShapeMeasurements.getPrecisionCenter(rect2, 90).getX()==-50.0);
		assertTrue(ShapeMeasurements.getPrecisionCenter(rect2, 90).getY()==50.0);
	}
	
	@Test
	public void testgetBottomRightCorner() {
		Rectangle rect2 = new Rectangle(100,100);
		assertTrue(ShapeMeasurements.getBottomRightCorner(rect2).getX()==100);
		assertTrue(ShapeMeasurements.getBottomRightCorner(rect2).getY()==100);
	}
	
	@Test
	public void testgetBottomLeftCorner() {
		Rectangle rect2 = new Rectangle(100,100);
		assertTrue(ShapeMeasurements.getBottomLeftCorner(rect2).getX()==0);
		assertTrue(ShapeMeasurements.getBottomLeftCorner(rect2).getY()==100);
	}
	

}
