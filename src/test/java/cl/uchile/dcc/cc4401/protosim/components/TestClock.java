package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestClock {

    private ClockChip chip;

    @Before
    public void setUp() {
        chip = new ClockChip();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("Clock Chip", chip.getDisplayName());
    }

    @Test
    public void testGetOffsetBounds() {
        Bounds bounds = chip.getOffsetBounds(null);
        assertEquals(0, bounds.getX());
        assertEquals(0, bounds.getY());
        assertEquals(20, bounds.getWidth());
        assertEquals(30, bounds.getHeight());
    }

    
    @Test
    public void testNotConnected() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN, //not connected
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN //not connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(2));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(4));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN //not connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(2));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(4));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN, //not connected
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(2));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(4));
    }
}
