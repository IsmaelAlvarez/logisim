package cl.uchile.dcc.cc4401.protosim.components;

import com.cburch.logisim.data.Bounds;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCapacitor {

    private Capacitor capacitor;

    @Before
    public void setUp() {
        capacitor = new Capacitor();
    }

    @Test
    public void testGetOffsetBounds() {
        Bounds bounds = capacitor.getOffsetBounds(null);
        assertEquals(-6, bounds.getX());
        assertEquals(-20, bounds.getY());
        assertEquals(20, bounds.getWidth());
        assertEquals(40, bounds.getHeight());
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("Capacitor", capacitor.getDisplayName());
    }
        

}

