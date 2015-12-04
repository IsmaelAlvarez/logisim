package cl.uchile.dcc.cc4401.protosim.components;

import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.StdAttr;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class ProtosimClockState implements Cloneable {
	private Value lastClock1;
	private Value lastClock2;

	public ProtosimClockState() {
		lastClock1 = ProtoValue.FALSE;
		lastClock2 = ProtoValue.FALSE;
	}

	@Override
	public ProtosimClockState clone() {
		try {
			return (ProtosimClockState) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public boolean updateClock(int gate, Value newClock, Object trigger) {
		Value oldClock = getLastClock(gate);
		setLastClock(gate, newClock);
		if (trigger == null || trigger == StdAttr.TRIG_RISING) {
			return oldClock.equals(ProtoValue.FALSE) && newClock.equals(ProtoValue.TRUE);
		} else if (trigger == StdAttr.TRIG_FALLING) {
			return oldClock.equals(ProtoValue.TRUE) && newClock.equals(ProtoValue.FALSE);
		} else if (trigger == StdAttr.TRIG_HIGH) {
			return newClock.equals(ProtoValue.TRUE);
		} else if (trigger == StdAttr.TRIG_LOW) {
			return newClock.equals(ProtoValue.FALSE);
		} else {
			return oldClock.equals(ProtoValue.FALSE) && newClock.equals(ProtoValue.TRUE);
		}
	}

	private Value getLastClock(int gate) {
		if (gate == 1) {
			return lastClock1;
		} else {
			return lastClock2;
		}
	}

	private void setLastClock(int gate, Value newValue) {
		if (gate == 1) {
			lastClock1 = newValue;
		} else {
			lastClock2 = newValue;
		}
	}
}