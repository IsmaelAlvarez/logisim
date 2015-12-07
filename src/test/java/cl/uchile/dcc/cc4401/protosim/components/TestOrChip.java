package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceState;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestOrChip {

    private OrChip chip;

    @Before
    public void setUp() {
        chip = new OrChip();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("OR Chip", chip.getDisplayName());
    }

    @Test
    public void testGetOffsetBounds() {
        Bounds bounds = chip.getOffsetBounds(null);
        assertEquals(0, bounds.getX());
        assertEquals(0, bounds.getY());
        assertEquals(60, bounds.getWidth());
        assertEquals(30, bounds.getHeight());
    }

    public void testPropagateInputPortsWithUnknownValues() {
        // All unknown values
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(9));

        // unknown input
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE, //connected

                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
            
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,

                ProtoValue.FALSE //connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.FALSE, state.getPort(2));
        assertEquals(ProtoValue.FALSE, state.getPort(4));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(8));
        assertEquals(ProtoValue.FALSE, state.getPort(10));
        assertEquals(ProtoValue.FALSE, state.getPort(12));

    }
    @Test
    public void testPropagateEmptyPortsValues() {
        InstanceState state = new StubInstanceState(new Value[] {
        		ProtoValue.TRUE, //connected

                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE //connected
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.FALSE, state.getPort(3));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(9));
        assertEquals(ProtoValue.FALSE, state.getPort(12));
    }
    
    @Test
    public void testPropagateOneInputPortWithValue() {
        // Port A
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE, //connected

                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,

                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.TRUE,

                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,

                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,

                ProtoValue.FALSE //connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.TRUE, state.getPort(3));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.TRUE, state.getPort(9));
        assertEquals(ProtoValue.TRUE, state.getPort(12));


        // Port B
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE, //connected

                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.FALSE //connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.TRUE, state.getPort(3));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.TRUE, state.getPort(9));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
    }
    @Test
    public void testPropagateBothInputPortsWithValue() {
        InstanceState state = new StubInstanceState(new Value[] {
        		ProtoValue.TRUE, //connected

                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.FALSE //connected
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.TRUE, state.getPort(3));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.TRUE, state.getPort(9));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
    }
    
    @Test
    public void testNotConnected() {
        InstanceState state = new StubInstanceState(new Value[] {
        		ProtoValue.UNKNOWN, //not connected

                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,

                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,

                ProtoValue.FALSE,
                ProtoValue.FALSE,
                ProtoValue.FALSE,

                ProtoValue.UNKNOWN //not connected
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(6));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(9));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(12));
        
       /* state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                Value.UNKNOWN,
                ProtoValue.FALSE,
                ProtoValue.TRUE,
                Value.UNKNOWN,
                ProtoValue.FALSE,
        });

        chip.propagate(state);
        
        assertEquals(ProtoValue.UNKNOWN, state.getPort(3));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(6));
        */
    }
}
