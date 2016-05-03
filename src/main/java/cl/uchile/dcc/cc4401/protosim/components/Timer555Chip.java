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
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.util.Icons;

public class Timer555Chip extends InstanceFactory{

	public static InstanceFactory FACTORY = new Timer555Chip();
	
	private List<Port> ports;

	private Attribute<AttributeOption> triggerAttribute;
	
	public Timer555Chip() {
		super("Timer555Chip");
		setIcon(Icons.getIcon("protosimComponentClock.svg"));
		triggerAttribute = StdAttr.TRIGGER;
		
		//Set ports
		ports = new ArrayList<Port>();
		
		//Upper ports
		ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));	//Vcc
		ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH));	//DIS
		ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH));	//THR
		ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH));	//CTRL
		
		//Lower Ports
		ports.add(new Port(0, 30, Port.INPUT, Breadboard.PORT_WIDTH));	//GND
		ports.add(new Port(10, 30, Port.INPUT, Breadboard.PORT_WIDTH));	//TRIG
		ports.add(new Port(20, 30, Port.INPUT, Breadboard.PORT_WIDTH));	//OUT
		ports.add(new Port(30, 30, Port.INPUT, Breadboard.PORT_WIDTH));	//RESET

		setPorts(ports);
		
		setAttributes(
				new Attribute[] { StdAttr.FACING, StdAttr.LABEL, Pin.ATTR_LABEL_LOC,
						StdAttr.LABEL_FONT},
				new Object[] { Direction.EAST, "", Direction.WEST, StdAttr.DEFAULT_LABEL_FONT});
	}

	@Override
	public Bounds getOffsetBounds(AttributeSet attrs){
		return Bounds.create(0, 0, 50, 30);
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
		g.drawString("555 TIMER IC", x + 3, y + 18);
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

	@Override
	public void propagate(InstanceState state) {
		// TODO Auto-generated method stub
		
	}

}
