package cl.uchile.dcc.cc4401.protosim.components;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.io.Io;
import com.cburch.logisim.util.Icons;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//clase impactada

public class Breadboard extends InstanceFactory {

	public static final int PORT_WIDTH = 32;
	public static final int DELAY = 1;

	public static InstanceFactory FACTORY = new Breadboard();
	
	private List<Port> ports;

	/*
	 * If two or more ports have the same integer value,
	 * they are connected in the breadboard
	 */
	private HashMap<Port, Integer> connected;
	public Breadboard() {
		super("Breadboard");
		setIcon(Icons.getIcon("protosimBreadboard.svg"));
		
		

		ports = new ArrayList<Port>();
		connected = new HashMap<Port, Integer>();

		createAndConnectPorts();
		createAttributeSet();
		setAttributes(new Attribute[] {
                Io.ATTR_COMPONENT_ID,
                StdAttr.LABEL,
                Io.ATTR_LABEL_LOC,
                StdAttr.LABEL_FONT,
                Io.ATTR_LABEL_COLOR,
            },
            new Object[] {
                null,
                "",
                Direction.NORTH,
                StdAttr.DEFAULT_LABEL_FONT,
                Color.BLACK,
            }
        );
		setPorts(ports);
	}
	
	private void createAndConnectPorts() {
		int pinGroup = 1;
		for (int i = 0; i <= 64 * 10; i += 10) {
			Port port = new Port(10 + i, 10, Port.INOUT, PORT_WIDTH);
			ports.add(port);
			connected.put(port, pinGroup);
		}
		pinGroup++;
		for (int i = 0; i <= 64 * 10; i += 10) {
			Port port = new Port(10 + i, 20, Port.INOUT, PORT_WIDTH);
			ports.add(port);
			connected.put(port, pinGroup);
		}
		pinGroup++;
		for (int i = 0; i <= 64 * 10; i += 10) {
			Port port = new Port(10 + i, 170, Port.INOUT, PORT_WIDTH);
			ports.add(port);
			connected.put(port, pinGroup);
		}
		pinGroup++;
		for (int i = 0; i <= 64 * 10; i += 10) {
			Port port = new Port(10 + i, 180, Port.INOUT, PORT_WIDTH);
			ports.add(port);
			connected.put(port, pinGroup);
		}
		pinGroup++;
		for (int i = 0; i <= 64 * 10; i += 10) {
			Port port1 = new Port(10 + i, 40, Port.INOUT, PORT_WIDTH);
			Port port2 = new Port(10 + i, 40 + 10, Port.INOUT, PORT_WIDTH);
			Port port3 = new Port(10 + i, 40 + 20, Port.INOUT, PORT_WIDTH);
			Port port4 = new Port(10 + i, 40 + 30, Port.INOUT, PORT_WIDTH);
			Port port5 = new Port(10 + i, 40 + 40, Port.INOUT, PORT_WIDTH);
			Port port6 = new Port(10 + i, 110, Port.INOUT, PORT_WIDTH);
			Port port7 = new Port(10 + i, 110 + 10, Port.INOUT, PORT_WIDTH);
			Port port8 = new Port(10 + i, 110 + 20, Port.INOUT, PORT_WIDTH);
			Port port9 = new Port(10 + i, 110 + 30, Port.INOUT, PORT_WIDTH);
			Port port10 = new Port(10 + i, 110 + 40, Port.INOUT, PORT_WIDTH);
			ports.add(port1);
			ports.add(port2);
			ports.add(port3);
			ports.add(port4);
			ports.add(port5);
			ports.add(port6);
			ports.add(port7);
			ports.add(port8);
			ports.add(port9);
			ports.add(port10);
			connected.put(port1, pinGroup);
			connected.put(port2, pinGroup);
			connected.put(port3, pinGroup);
			connected.put(port4, pinGroup);
			connected.put(port5, pinGroup);
			pinGroup++;
			connected.put(port6, pinGroup);
			connected.put(port7, pinGroup);
			connected.put(port8, pinGroup);
			connected.put(port9, pinGroup);
			connected.put(port10, pinGroup);
			pinGroup++;
		}	
	}

	@Override
	public Bounds getOffsetBounds(AttributeSet attrs) {
		return Bounds.create(0, 0, 660, 190);
	}

	@Override
	public void paintInstance(InstancePainter painter) {
		Location loc = painter.getLocation();
		int x = loc.getX();
		int y = loc.getY();

		Graphics g = painter.getGraphics();
		g.setColor(Color.black);
		g.drawRect(x, y, 660, 190);

		// Breadboard lines
		g.setColor(Color.red);
		g.drawLine(x + 10, y + 26, x + 650, y + 26);
		g.drawLine(x + 10, y + 186, x + 650, y + 186);

		g.setColor(Color.blue);
		g.drawLine(x + 10, y + 3, x + 650, y + 3);
		g.drawLine(x + 10, y + 164, x + 650, y + 164);

		painter.drawPorts();
	}

	@Override
	public void propagate(InstanceState state) {
		// TODO Auto-generated method stub
	}

	public HashMap<Port, Integer> getConnected() {
		return connected;
	}

}
