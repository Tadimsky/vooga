package vooga.fighter.util;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import util.Location;
import util.Vector;

public class Physicstester {
    private static final Vector STILL = new Vector();
    private static final Vector UP = new Vector(90, 10);
    private static final Vector DOWN = new Vector(270, 10);
    private static final Vector LEFT = new Vector(180, 10);
    private static final Vector RIGHT = new Vector(0, 10);
    private static final Vector RIGHTUP = new Vector(45, 10);
    private static final Vector RIGHTDOWN = new Vector(315, 10);
    private static final Vector LEFTUP = new Vector(135, 10);
    private static final Vector LEFTDOWN = new Vector(225, 10);
    private static final int MASS = 10;

    @Before
    public void setUp () throws Exception {
        
    }

    @Test
    public void testElasticCollision () {
        fail("Not yet implemented");
    }

    @Test
    public void testInelasticCollision () {
        Vector[] newVel = Physics.inelasticCollision(UP, new Location(0,-5), MASS, STILL, new Location(0,0), MASS);
        assertEquals(90.0,newVel[0].getDirection(),2);
        assertEquals(90.0,newVel[1].getDirection(),2);
        assertEquals(newVel[0].getMagnitude(),newVel[1].getMagnitude(),.01);
    }

    @Test
    public void testPartiallyInelasticCollision () {
        fail("Not yet implemented");
    }

    @Test
    public void testGravity () {
        fail("Not yet implemented");
    }

    @Test
    public void testFriction () {
        fail("Not yet implemented");
    }

    @Test
    public void testSliding () {
        fail("Not yet implemented");
    }

    @Test
    public void testBounce () {
        fail("Not yet implemented");
    }

    @Test
    public void testApplyForceVelocityDoubleVelocity () {
        fail("Not yet implemented");
    }

    @Test
    public void testApplyForceVelocityDoubleVelocityDouble () {
        fail("Not yet implemented");
    }

}
