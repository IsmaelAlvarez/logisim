package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.cburch.logisim.data.Bounds;


public class TestLED {

    private Led led;

    @Before
    public void setUp() {
    	led = new Led();
    }

    @Test
    public void testGetOffsetBounds() {
        Bounds bounds = led.getOffsetBounds(null);
        assertEquals(-6, bounds.getX());
        assertEquals(-6, bounds.getY());
        assertEquals(20, bounds.getWidth());
        assertEquals(25, bounds.getHeight());
    }
}
