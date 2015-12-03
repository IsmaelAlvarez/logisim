package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestAndChip {

    private AndChip chip;

    @Before
    public void setUp() {
        chip = new AndChip();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("AND Chip", chip.getDisplayName());
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
    public void testPropagateInputPortsWithUnknownValues() {
        // All unknown values
        InstanceState state = new StubInstanceState(new Value[] {
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
                Value.UNKNOWN,
        });

        chip.propagate(state);

        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));

        // Port A unknown
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));


        // Port B unknown
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
    }

    @Test
    public void testPropagateEmptyPortsValues() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
    }
    
    @Test
    public void testPropagateOneInputPortWithValue() {
        // Port A
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));


        // Port B
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
    }

    @Test
    public void testPropagateBothInputPortsWithValue() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.TRUE, state.getPort(3));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
    }

}
