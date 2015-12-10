package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeOption;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.memory.Memory;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class FlipFlopChip extends InstanceFactory {

	private Attribute<AttributeOption> triggerAttribute;

	public static InstanceFactory FACTORY = new FlipFlopChip();

	private List<Port> ports;

	public FlipFlopChip() {

		super("FlipFlopChip");
		setIconName("protosimComponentFlipFlop.svg");

		triggerAttribute = StdAttr.TRIGGER;
		setAttributes(new Attribute[] { triggerAttribute, StdAttr.LABEL, StdAttr.LABEL_FONT },
				new Object[] { StdAttr.TRIG_RISING, "", StdAttr.DEFAULT_LABEL_FONT });

		ports = new ArrayList<Port>();

		// Upper ports
		ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // vcc
		ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // clr
		ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // d
		ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // clk
		ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // pr
		ports.add(new Port(50, 0, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q
		ports.add(new Port(60, 0, Port.OUTPUT, Breadboard.PORT_WIDTH)); // not_q

		// Lower ports
		ports.add(new Port(0, 30, Port.INOUT, Breadboard.PORT_WIDTH)); // clr
		ports.add(new Port(10, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // d
		ports.add(new Port(20, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // clk
		ports.add(new Port(30, 30, Port.INOUT, Breadboard.PORT_WIDTH)); // pr
		ports.add(new Port(40, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q
		ports.add(new Port(50, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // not_q
		ports.add(new Port(60, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // gnd

		setPorts(ports);
	}

	public String getDisplayName() {
		return "Flip-Flop Chip";
	}

	@Override
	public void paintInstance(InstancePainter painter) {
		Location loc = painter.getLocation();
		int x = loc.getX();
		int y = loc.getY();

		Graphics g = painter.getGraphics();

		// Chip
		g.setColor(Color.black);
		g.fillRect(x - 2, y + 5, 64, 20);

		// Text
		g.setColor(Color.white);
		g.setFont(new Font("Courier", Font.BOLD, 8));
		g.drawString("FLIP FLOP", x + 8, y + 13);
		g.drawString("7474", x + 18, y + 22);

		g.drawString("+", x - 1, y + 12);
		g.drawString("-", x + 57, y + 24);

		// Pins
		g.setColor(Color.gray);
		g.fillRect(x - 2, y, 4, 5);
		g.fillRect(x + 8, y, 4, 5);
		g.fillRect(x + 18, y, 4, 5);
		g.fillRect(x + 28, y, 4, 5);
		g.fillRect(x + 38, y, 4, 5);
		g.fillRect(x + 48, y, 4, 5);
		g.fillRect(x + 58, y, 4, 5);

		g.fillRect(x - 2, y + 25, 4, 5);
		g.fillRect(x + 8, y + 25, 4, 5);
		g.fillRect(x + 18, y + 25, 4, 5);
		g.fillRect(x + 28, y + 25, 4, 5);
		g.fillRect(x + 38, y + 25, 4, 5);
		g.fillRect(x + 48, y + 25, 4, 5);
		g.fillRect(x + 58, y + 25, 4, 5);

		painter.drawPorts();
	}

	@Override
	public Bounds getOffsetBounds(AttributeSet attrs) {
		return Bounds.create(0, 0, 60, 30);
	}

	@Override
	public void propagate(InstanceState state) {
		StateData data = (StateData) state.getData();
		if (data == null) {
			data = new StateData();
			state.setData(data);
		}
		Object triggerType = state.getAttributeValue(triggerAttribute);

		boolean triggered1 = data.updateClock(1, state.getPort(3), triggerType);
		boolean triggered2 = data.updateClock(2, state.getPort(9), triggerType);

		Value valueVcc = state.getPort(0);
		Value valueGround = state.getPort(13);

		if (ProtoValue.isEnergized(valueVcc, valueGround)) {
			setOutputValue(state, data, 1, triggered1, 1, 2, 4, 5, 6);
			setOutputValue(state, data, 2, triggered2, 7, 8, 10, 11, 12);
		} else {
			state.setPort(5, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(6, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(11, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(12, ProtoValue.UNKNOWN, Breadboard.DELAY);
		}
	}

	private void setOutputValue(InstanceState state, StateData data, int gate, boolean triggered, int clr, int d,
			int pr, int q, int not_q) {
		// clear requested
		if (state.getPort(clr) == ProtoValue.TRUE) {
			data.setValue(gate, ProtoValue.FALSE);
			// preset requested
		} else if (state.getPort(pr) == ProtoValue.TRUE) {
			data.setValue(gate, ProtoValue.TRUE);
		} else if (triggered) {
			// Clock has triggered
			Value newVal = state.getPort(d);
			if (newVal.equals(ProtoValue.TRUE) || newVal.equals(ProtoValue.FALSE)) {
				data.setValue(gate, newVal);
			}
		}
		state.setPort(q, data.getValue(gate), Memory.DELAY);
		state.setPort(not_q, data.getValue(gate).not(), Memory.DELAY);
	}

	private static class StateData extends ProtosimClockState implements InstanceData {
		Value curValue1 = ProtoValue.FALSE;
		Value curValue2 = ProtoValue.FALSE;

		Value getValue(int gate) {
			if (gate == 1) {
				return curValue1;
			} else {
				return curValue2;
			}
		}

		void setValue(int gate, Value newValue) {
			if (gate == 1) {
				curValue1 = newValue;
			} else {
				curValue2 = newValue;
			}
		}
	}
}
