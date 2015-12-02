package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestFlipFlopChip {

    private FlipFlopChip chip;

    @Before
    public void setUp() {
        chip = new FlipFlopChip();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("Flip-Flop Chip", chip.getDisplayName());
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
        });

        chip.propagate(state);

        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));

        // Data port value unknown
        state = new StubInstanceState(new Value[] {
                Value.UNKNOWN,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
        
        state = new StubInstanceState(new Value[] {
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));


        // Clock port value unknown
        state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.TRUE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
    }

    @Test
    public void testPropagateEmptyPortsValues() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
    }

    @Test
    public void testPropagate() {
        // Clock value 0
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });
        
        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
        
        // Retain data, Clock value 1
        state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
        });
        
        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));

        // Retain data, Clock value 0
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });
        
        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(5));
        
        // New data, Clock value 1
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
        });
        
        chip.propagate(state);
        
        assertEquals(ProtoValue.TRUE, state.getPort(2));
        assertEquals(ProtoValue.TRUE, state.getPort(5));

        // Retain data, Clock value 0
        state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
        });
        
        chip.propagate(state);
        
        assertEquals(ProtoValue.TRUE, state.getPort(2));
        assertEquals(ProtoValue.TRUE, state.getPort(5));
    }

}
