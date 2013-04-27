package vooga.rts.tests;

import static org.junit.Assert.*;
import java.awt.Dimension;
import org.junit.BeforeClass;
import org.junit.Test;
import vooga.rts.util.Dimension3D;
import vooga.rts.util.IsometricConverter;
import vooga.rts.util.Location3D;

/**
 * 
 * @author Jonathan Schmidt
 *
 */
public class TestIsometricConversions {

    @BeforeClass
    public static void setUpBeforeClass () throws Exception {
    }

    @Test
    public void testDimensionConversion () {
        Dimension test = new Dimension(100, 100);
        Dimension3D result = IsometricConverter.toIsometric(test);
        System.out.println(result);
        assertEquals(result.toString(), new Dimension3D(100, 50, 75).toString());
    }
    
    @Test
    public void testPaintLocation () {
        Dimension3D test = new Dimension3D(100, 25, 150);
        Location3D base = new Location3D(300, 300, 0);
        Location3D result = IsometricConverter.calculatePaintLocation(base, test);
        System.out.println(result);
        assertEquals(result.toString(), new Location3D(250, 325, 150).toString());
    }

}
