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
        assertEquals(60, bounds.getWidth());
        assertEquals(30, bounds.getHeight());
    }
    /*
    @Test
    public void testNotConnected(){
    	InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN, //not connected
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN //not connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(5));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(6));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(11));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(12));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN, //not connected
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.FALSE
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(5));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(6));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(11));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(12));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.TRUE,
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN //not connected
        });

        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(5));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(6));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(11));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(12));
    }*/
    
    @Test
    public void testPropagateUpPulse(){
    	//clock is down
    	Value[] values =new Value[]{
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // data input
                ProtoValue.FALSE, // clk
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // q
                ProtoValue.TRUE, // not_q
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // data input
                ProtoValue.FALSE, // clk
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // q
                ProtoValue.TRUE, // not_q
                ProtoValue.FALSE
        };
    	InstanceState state = new StubInstanceState(values);
    	
    	chip.propagate(state);
    	
    	assertEquals(ProtoValue.FALSE, state.getPort(5));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(11));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
        
        //input data changes
        values[2]=ProtoValue.TRUE;
        values[8]=ProtoValue.TRUE;
        
        chip.propagate(state);
    	
        //output doesn't change
    	assertEquals(ProtoValue.FALSE, state.getPort(5));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(11));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
        
        //clock changes to up state
        values[3]=ProtoValue.TRUE;
        values[9]=ProtoValue.TRUE;
        
        chip.propagate(state);
        
        //output changes
    	assertEquals(ProtoValue.TRUE, state.getPort(5));
        assertEquals(ProtoValue.FALSE, state.getPort(6));
        assertEquals(ProtoValue.TRUE, state.getPort(11));
        assertEquals(ProtoValue.FALSE, state.getPort(12));    
    }
    
    @Test
    public void testPropagateDownPulse(){
    	//clock is up
    	Value[] values =new Value[]{
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // data input
                ProtoValue.TRUE, // clk
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // q
                ProtoValue.TRUE, // not_q
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // data input
                ProtoValue.TRUE, // clk
                ProtoValue.UNKNOWN,
                ProtoValue.FALSE, // q
                ProtoValue.TRUE, // not_q
                ProtoValue.FALSE
        };
    	InstanceState state = new StubInstanceState(values);
    	
    	chip.propagate(state);
    	
    	assertEquals(ProtoValue.FALSE, state.getPort(5));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(11));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
        
        //input data changes
        values[2]=ProtoValue.TRUE;
        values[8]=ProtoValue.TRUE;
        
        chip.propagate(state);
    	
        //output doesn't change
    	assertEquals(ProtoValue.FALSE, state.getPort(5));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(11));
        assertEquals(ProtoValue.TRUE, state.getPort(12));
        
        //clock changes to down state
        values[3]=ProtoValue.FALSE;
        values[9]=ProtoValue.FALSE;
        
        chip.propagate(state);
        
        //output doesn't changes
      	assertEquals(ProtoValue.FALSE, state.getPort(5));
        assertEquals(ProtoValue.TRUE, state.getPort(6));
        assertEquals(ProtoValue.FALSE, state.getPort(11));
        assertEquals(ProtoValue.TRUE, state.getPort(12));    
    }

}
