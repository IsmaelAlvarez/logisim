package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceDataSingleton;
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
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
        });
        
        assertEquals(ProtoValue.UNKNOWN, state.getPort(0));
        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        
        led.propagate(state);
        
        InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();
        assertEquals(Value.FALSE, val);


    }
    
    @Test
    public void testPropagateInputPortsWithTrueValues() {
        // All TRUE values
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.TRUE,
        });
        
        assertEquals(ProtoValue.TRUE, state.getPort(0));
        assertEquals(ProtoValue.TRUE, state.getPort(1));
        
        led.propagate(state);
        
        InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();
        assertEquals(ProtoValue.UNKNOWN, val);

    }
    
    @Test
    public void testPropagateInputPortsWithfalseValues() {
        // All FALSE values
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.FALSE,
        });

        assertEquals(ProtoValue.FALSE, state.getPort(0));
        assertEquals(ProtoValue.FALSE, state.getPort(1));
        
        led.propagate(state);
        
        InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();
        assertEquals(ProtoValue.FALSE, val);


    }
    
    @Test
    public void testLedOn(){
    	 InstanceState state = new StubInstanceState(new Value[] {
                 ProtoValue.TRUE,
                 ProtoValue.FALSE,
         });

         assertEquals(ProtoValue.TRUE, state.getPort(0));
         assertEquals(ProtoValue.FALSE, state.getPort(1));
         
         led.propagate(state);
         
         InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
         Value val = data == null ? Value.FALSE : (Value) data.getValue();
         assertEquals(ProtoValue.TRUE, val);	
    }
    
    @Test
    public void testLedOff(){
    	 InstanceState state = new StubInstanceState(new Value[] {
                 ProtoValue.FALSE,
                 ProtoValue.TRUE,
         });

         assertEquals(ProtoValue.FALSE, state.getPort(0));
         assertEquals(ProtoValue.TRUE, state.getPort(1));
         
         led.propagate(state);
         
         InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
         Value val = data == null ? Value.FALSE : (Value) data.getValue();
         assertEquals(Value.FALSE, val);	
    }

}
