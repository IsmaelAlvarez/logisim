package cl.uchile.dcc.cc4401.protosim.components;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceDataSingleton;
import com.cburch.logisim.instance.InstanceState;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class TestSwitch {

    private Switch chip;

    @Before
    public void setUp() {
        chip = new Switch();
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("Switch", chip.getDisplayName());
    }
    
    @Test
    public void testPropagateInputSwitchClicked() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
        });
        InstanceDataSingleton stubData = new InstanceDataSingleton(ProtoValue.TRUE);
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.TRUE, state.getPort(1));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
        });
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.FALSE, state.getPort(1));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
        });
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
    }
    
    @Test
    public void testPropagateInputSwitchNotClicked() {
        InstanceState state = new StubInstanceState(new Value[] {
                ProtoValue.TRUE,
                ProtoValue.UNKNOWN,
        });
        InstanceDataSingleton stubData = new InstanceDataSingleton(ProtoValue.FALSE);
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.FALSE,
                ProtoValue.UNKNOWN,
        });
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
        
        state = new StubInstanceState(new Value[] {
                ProtoValue.UNKNOWN,
                ProtoValue.UNKNOWN,
        });
        state.setData(stubData);
        chip.propagate(state);

        assertEquals(ProtoValue.UNKNOWN, state.getPort(1));
    }
}