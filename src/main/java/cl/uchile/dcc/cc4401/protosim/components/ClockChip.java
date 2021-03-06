package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.circuit.CircuitState;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.Instance;
import com.cburch.logisim.instance.InstanceData;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstanceLogger;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstancePoker;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Clock;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.std.wiring.Probe;
import com.cburch.logisim.util.Icons;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class ClockChip extends AbstractComponent {

	public static InstanceFactory FACTORY = new ClockChip();

	private List<Port> ports;

	public ClockChip() {
		super("Clock Chip");
		this.setIcon(Icons.getIcon("protosimComponentClock.svg"));

		ports = new ArrayList<Port>();

		// Upper ports
		ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));
		ports.add(new Port(10, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));
		ports.add(new Port(20, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));

		// Lower ports
		ports.add(new Port(0, 30, Port.OUTPUT, Breadboard.PORT_WIDTH));
		ports.add(new Port(10, 30, Port.OUTPUT, Breadboard.PORT_WIDTH));
		ports.add(new Port(20, 30, Port.INPUT, Breadboard.PORT_WIDTH));

		setPorts(ports);

		setAttributes(
				new Attribute[] { StdAttr.FACING, Clock.ATTR_HIGH, Clock.ATTR_LOW, StdAttr.LABEL, Pin.ATTR_LABEL_LOC,
						StdAttr.LABEL_FONT },
				new Object[] { Direction.EAST, Integer.valueOf(1), Integer.valueOf(1), "", Direction.WEST,
						StdAttr.DEFAULT_LABEL_FONT });

		setFacingAttribute(StdAttr.FACING);
		setInstanceLogger(ClockLogger.class);
		setInstancePoker(ClockPoker.class);
	}

	@Override
	public Bounds getOffsetBounds(AttributeSet attrs) {
		return Bounds.create(0, 0, 20, 30);
	}

	@Override
	public void paintInstance(InstancePainter painter) {
		Location loc = painter.getLocation();
		int x = loc.getX();
		int y = loc.getY();

		Graphics g = painter.getGraphics();

		// Chip
		g.setColor(Color.black);
		g.fillRect(x - 2, y + 5, 24, 20);

		// Text
		g.setColor(Color.white);
		g.setFont(new Font("Courier", Font.BOLD, 8));
		g.drawString("CLK", x + 3, y + 18);
		g.drawString("+", x - 1, y + 12);
		g.drawString("-", x + 17, y + 24);

		// Pins
		g.setColor(Color.gray);
		g.fillRect(x - 2, y, 4, 5);
		g.fillRect(x + 8, y, 4, 5);
		g.fillRect(x + 18, y, 4, 5);

		g.fillRect(x - 2, y + 25, 4, 5);
		g.fillRect(x + 8, y + 25, 4, 5);
		g.fillRect(x + 18, y + 25, 4, 5);

		painter.drawPorts();
	}

	private static class ClockState implements InstanceData, Cloneable {
		Value sending = ProtoValue.FALSE;
		int clicks = 0;

		@Override
		public ClockState clone() {
			try {
				return (ClockState) super.clone();
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}
	}

	public static class ClockLogger extends InstanceLogger {
		@Override
		public String getLogName(InstanceState state, Object option) {
			return state.getAttributeValue(StdAttr.LABEL);
		}

		@Override
		public Value getLogValue(InstanceState state, Object option) {
			ClockState s = getState(state);
			return s.sending;
		}
	}

	public static class ClockPoker extends InstancePoker {
		boolean isPressed = true;

		@Override
		public void mousePressed(InstanceState state, MouseEvent e) {
			isPressed = isInside(state, e);
		}

		@Override
		public void mouseReleased(InstanceState state, MouseEvent e) {
			if (isPressed && isInside(state, e)) {
				ClockState myState = (ClockState) state.getData();
				myState.sending = myState.sending.not();
				myState.clicks++;
				state.fireInvalidated();
			}
			isPressed = false;
		}

		private boolean isInside(InstanceState state, MouseEvent e) {
			Bounds bds = state.getInstance().getBounds();
			return bds.contains(e.getX(), e.getY());
		}
	}

	//
	// methods for instances
	//
	@Override
	protected void configureNewInstance(Instance instance) {
		instance.addAttributeListener();
		configureLabel(instance);
	}

	@Override
	protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
		if (attr == Pin.ATTR_LABEL_LOC) {
			configureLabel(instance);
		} else if (attr == StdAttr.FACING) {
			instance.recomputeBounds();
			configureLabel(instance);
		}
	}

	@Override
	public void propagate(InstanceState state) {
		Value val = state.getPort(2);
		ClockState q = getState(state);

		Value valueVcc = state.getPort(0);
		Value valueGround = state.getPort(5);

		boolean isEnergized = ProtoValue.isEnergized(valueVcc, valueGround);

		// ignore if no change
		if ( ! val.equals(q.sending) && isEnergized) {
			state.setPort(1, q.sending, 1);
			state.setPort(2, q.sending, 1);
			state.setPort(3, q.sending, 1);
			state.setPort(4, q.sending, 1);
		}
		if ( ! isEnergized) {
			state.setPort(1, ProtoValue.UNKNOWN, 1);
			state.setPort(2, ProtoValue.UNKNOWN, 1);
			state.setPort(3, ProtoValue.UNKNOWN, 1);
			state.setPort(4, ProtoValue.UNKNOWN, 1);
		}
	}

	//
	// package methods
	//
	public static boolean tick(CircuitState circState, int ticks, Component comp) {
		AttributeSet attrs = comp.getAttributeSet();
		int durationHigh = attrs.getValue(Clock.ATTR_HIGH).intValue();
		int durationLow = attrs.getValue(Clock.ATTR_LOW).intValue();
		ClockState state = (ClockState) circState.getData(comp);
		if (state == null) {
			state = new ClockState();
			circState.setData(comp, state);
		}
		boolean curValue = ticks % (durationHigh + durationLow) < durationLow;
		if (state.clicks % 2 == 1) {
			curValue = !curValue;
		}

		Value desired = (curValue ? ProtoValue.FALSE : ProtoValue.TRUE);
		if (!state.sending.equals(desired)) {
			state.sending = desired;
			Instance.getInstanceFor(comp).fireInvalidated();
			return true;
		}
		return false;
	}

	//
	// private methods
	//
	private void configureLabel(Instance instance) {
		Direction facing = instance.getAttributeValue(StdAttr.FACING);
		Direction labelLoc = instance.getAttributeValue(Pin.ATTR_LABEL_LOC);
		Probe.configureLabel(instance, labelLoc, facing);
	}

	private static ClockState getState(InstanceState state) {
		ClockState ret = (ClockState) state.getData();
		if (ret == null) {
			ret = new ClockState();
			state.setData(ret);
		}
		return ret;
	}
}
