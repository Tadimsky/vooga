package vooga.rts.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.junit.Test;
import util.Location;
import vooga.rts.util.Camera;
import vooga.rts.util.Location3D;


public class TestCamera {

    // Camera myCamera = new Camera(new Location3D());

    @Test
    public void test () {
        fail("Not yet implemented");
    }

    @Test
    public void TestBasicWorldtoScreen () {
        Location3D loc = new Location3D(0, 0, 0);
        Location res = (Location) Camera.instance().worldToView(loc);
        System.out.println(res);
        assertEquals(res, new Location(0, 0));
    }

    @Test
    public void TestPositiveWorldtoScreen () {
        Location3D loc = new Location3D(100, 50, 0);
        Location res = (Location) Camera.instance().worldToView(loc);
        System.out.println(res);
        // assertEquals(res, new Location(0, 0));
    }

    @Test
    public void TestRectangleViewtoWorld () {
        Rectangle2D r = new Rectangle2D.Double(0, 0, 100, 100);
        Shape f = Camera.instance().viewtoWorld(r);
        System.out.println(f);
        assertTrue(true);
    }

}
