package vooga.rts.tests;

import static org.junit.Assert.*;
import java.awt.Dimension;
import org.junit.BeforeClass;
import org.junit.Test;
import vooga.rts.util.IsometricConverter;

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
        IsometricConverter.toIsometric();
    }
    
    @Test
    public void testPaintLocation () {
        fail("Not yet implemented");
    }

}
