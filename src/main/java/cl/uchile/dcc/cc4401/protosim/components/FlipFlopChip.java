package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
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
import com.cburch.logisim.instance.InstanceLogger;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstancePoker;
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
		setInstancePoker(Poker.class);
		setInstanceLogger(Logger.class);

		ports = new ArrayList<Port>();

		// Upper ports
		ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // 0 vcc
		ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // 1 clr
		ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // 2 d
		ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // 3 clk
		ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // 4 pr
		ports.add(new Port(50, 0, Port.OUTPUT, Breadboard.PORT_WIDTH)); // 5 q
		ports.add(new Port(60, 0, Port.OUTPUT, Breadboard.PORT_WIDTH)); // 6
																		// not_q

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
		g.drawString("FLIP FLOP", x + 9, y + 13);
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

	private boolean isEnergized(InstanceState state, int vcc, int ground) {
		Value valueVCC = state.getPort(vcc);
		Value valueGround = state.getPort(ground);
		if (valueVCC.isFullyDefined() && valueGround.isFullyDefined()
				&& valueVCC.toIntValue() == ProtoValue.TRUE.toIntValue()
				&& valueGround.toIntValue() == ProtoValue.FALSE.toIntValue()) {
			return true;
		}
		return false;
	}

	@Override
	public void propagate(InstanceState state) {
		StateData data = (StateData) state.getData();
		if (data == null) {
			data = new StateData();
			state.setData(data);
		}
		Object triggerType = state.getAttributeValue(triggerAttribute);

		boolean triggered1 = data.updateClock(state.getPort(3), triggerType);
		// boolean triggered2 = data.updateClock(state.getPort(9), triggerType);

		if (isEnergized(state, 0, 13)) {
			setOutputValue(state, data, triggered1, 1, 2, 4, 5, 6);
			// setOutputValue(state, data, triggered2, 7, 8, 10, 11, 12);
		} else {
			state.setPort(5, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(6, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(11, ProtoValue.UNKNOWN, Breadboard.DELAY);
			state.setPort(12, ProtoValue.UNKNOWN, Breadboard.DELAY);
		}
	}

	private void setOutputValue(InstanceState state, StateData data, boolean triggered, int clr, int d, int pr, int q,
			int not_q) {
		 // clear requested
        if (state.getPort(clr) == ProtoValue.TRUE) {
            data.curValue = ProtoValue.FALSE;
        // preset requested
        } else if (state.getPort(pr) == ProtoValue.TRUE) {
            data.curValue = ProtoValue.TRUE;
        } else if (triggered) {
			// Clock has triggered and flip-flop is enabled: Update the state
			Value newVal = state.getPort(d);
			if (newVal.equals(ProtoValue.TRUE) || newVal.equals(ProtoValue.FALSE)) {
				data.curValue = newVal;
			}
		}
		state.setPort(q, data.curValue, Memory.DELAY);
		state.setPort(not_q, data.curValue.not(), Memory.DELAY);
	}

	private static class StateData extends ProtosimClockState implements InstanceData {
		Value curValue = ProtoValue.FALSE;
	}

	public static class Logger extends InstanceLogger {
		@Override
		public String getLogName(InstanceState state, Object option) {
			String ret = state.getAttributeValue(StdAttr.LABEL);
			return ret != null && !ret.equals("") ? ret : null;
		}

		@Override
		public Value getLogValue(InstanceState state, Object option) {
			StateData s = (StateData) state.getData();
			return s == null ? ProtoValue.FALSE : s.curValue;
		}
	}

	public static class Poker extends InstancePoker {
		boolean isPressed = true;

		@Override
		public void mousePressed(InstanceState state, MouseEvent e) {
			isPressed = isInside(state, e);
		}

		@Override
		public void mouseReleased(InstanceState state, MouseEvent e) {
			if (isPressed && isInside(state, e)) {
				StateData myState = (StateData) state.getData();
				if (myState == null) {
					return;
				}

				myState.curValue = myState.curValue.not();
				state.fireInvalidated();
			}
			isPressed = false;
		}

		private boolean isInside(InstanceState state, MouseEvent e) {
			Location loc = state.getInstance().getLocation();
			int dx = e.getX() - (loc.getX() - 20);
			int dy = e.getY() - (loc.getY() + 10);
			int d2 = dx * dx + dy * dy;
			return d2 < 8 * 8;
		}
	}
}
