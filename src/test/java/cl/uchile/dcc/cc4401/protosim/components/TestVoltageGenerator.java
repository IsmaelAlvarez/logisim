package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestVoltageGenerator {

    private VoltageGenerator voltage;

    @Before
    public void setUp() {
        voltage = new VoltageGenerator();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("Voltage Generator", voltage.getDisplayName());
    }

    @Test
    public void testGetOffsetBounds() {
        Bounds bounds = voltage.getOffsetBounds(null);
        assertEquals(-2, bounds.getX());
        assertEquals(2, bounds.getY());
        assertEquals(32, bounds.getWidth());
        assertEquals(25, bounds.getHeight());
    }

 

    @Test
    public void testOutputs() {
     InstanceState state1 = new StubInstanceState(new Value[] {
                Value.UNKNOWN,
                Value.UNKNOWN,
        });
     
     InstanceState state2 = new StubInstanceState(new Value[] {
             ProtoValue.TRUE,
             ProtoValue.TRUE
     });
     
        voltage.propagate(state1);
        
        assertEquals(ProtoValue.TRUE, state1.getPort(0));
        assertEquals(ProtoValue.FALSE, state1.getPort(1));
        
        voltage.propagate(state2);
        
        assertEquals(ProtoValue.TRUE, state2.getPort(0));
        assertEquals(ProtoValue.FALSE, state2.getPort(1));
    }
}
