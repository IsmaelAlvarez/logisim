package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

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
    
    @Test
    public void testGetDisplayName() {
        assertEquals("LED", led.getDisplayName());
    }
    
    @Test
    public void testPropagateInputPortsWithUnknownValues() {
        // All unknown values
        InstanceState state = new StubInstanceState(new Value[] {
                Value.UNKNOWN,
                Value.UNKNOWN,
        });

        led.propagate(state);

        assertEquals(Value.UNKNOWN, state.getPort(0));
        assertEquals(Value.UNKNOWN, state.getPort(1));

    }
    
    @Test
    public void testPropagateInputPortsWithknownValues() {
        // All known values
        InstanceState state = new StubInstanceState(new Value[] {
                Value.TRUE,
                Value.TRUE,
        });

        led.propagate(state);

        assertEquals(Value.TRUE, state.getPort(0));
        assertEquals(Value.TRUE, state.getPort(1));

    }
    
    @Test
    public void testPropagateInputPortsWithfalseValues() {
        // All false values
        InstanceState state = new StubInstanceState(new Value[] {
                Value.FALSE,
                Value.FALSE,
        });

        led.propagate(state);

        assertEquals(Value.FALSE, state.getPort(0));
        assertEquals(Value.FALSE, state.getPort(1));

    }

}
