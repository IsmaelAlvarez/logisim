package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceDataSingleton;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.io.Io;
import com.cburch.logisim.std.io.Led.Logger;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.Icons;

public class Led extends InstanceFactory {
    
    public static InstanceFactory FACTORY = new Led();
    
    List<Port> ports;

    public Led() {
        super("LED");
        setAttributes(new Attribute[] {
                StdAttr.FACING,
                Io.ATTR_ON_COLOR,
                Io.ATTR_OFF_COLOR,
                Io.ATTR_ACTIVE,
                StdAttr.LABEL,
                Io.ATTR_LABEL_LOC,
                StdAttr.LABEL_FONT,
                Io.ATTR_LABEL_COLOR
            },
            new Object[] {
                Direction.WEST,
                new Color(240, 0, 0),
                Color.DARK_GRAY,
                Boolean.TRUE,
                "",
                Io.LABEL_CENTER,
                StdAttr.DEFAULT_LABEL_FONT,
                Color.BLACK
            }
        );

        setFacingAttribute(StdAttr.FACING);
        this.setIcon(Icons.getIcon("protosimComponentLed.svg"));
        ports = new ArrayList<Port>();

        // Lower ports
        //for the moment, i put the port width in 1 bit Breadboard.PORT_WIDTH
        //the pin 0 is where our received the voltage, and the pin 1 is ground (ground is 0 always) or our for the moment show a exception
        ports.add(new Port(0, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        setPorts(ports);
        setInstanceLogger(Logger.class);
    }
    
    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(-6, -6, 20, 25);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();
        InstanceDataSingleton data = (InstanceDataSingleton) painter.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();
     
        Graphics g = painter.getGraphics();
        
        if (painter.getShowState()) { 

            if (val.equals(ProtoValue.TRUE)) {
                g.setColor(Color.green);
            }
            
            else if (val.equals(ProtoValue.FALSE)
                    || val.equals(ProtoValue.NOT_CONNECTED)
                    || val.toString().equals("0")) {
                g.setColor(Color.gray);
            }
            
            else if (val.equals(ProtoValue.UNKNOWN)) {
                g.setColor(Color.red);
            }
            
            g.fillOval(x - 2, y - 6, 14, 16);
            
          if (val.equals(ProtoValue.UNKNOWN)) {
                g.setColor(Color.white);
                g.setFont(new Font("Courier", Font.BOLD, 9));
                g.drawString("E", x + 3, y + 4);
            }
        }

        // Chip
        g.setColor(Color.black);
        g.fillRect(x - 5, y + 5, 20, 6);
        GraphicsUtil.drawCenteredArc(g, x+5, y+1, 7, 0, 180);
        g.drawLine(x - 2, y + 5, x - 2, y + 1);
        g.drawLine(x + 12, y + 5, x + 12, y + 1);
        
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 7));
        g.drawString("+", x - 3, y + 10);
        g.drawString("-", x + 8, y + 10);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x - 2, y + 11, 4, 9);
        g.fillRect(x + 8, y + 11, 4, 9);

        painter.drawPorts();
    }

    @Override
    public void propagate(InstanceState state) {
        Value val = state.getPort(0); // the val of the signal, receive the voltage
        Value valGround = state.getPort(1); // the val of the ground, 0 is ground
        InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
        
        // 0 is ground , if this value is x (not connected) or 1 (deadshort)
        // the ground is incorrectly connected.
        if (valGround.equals(ProtoValue.FALSE)) {
            if (data == null) {
                state.setData(new InstanceDataSingleton(val));
            } else {
                data.setValue(val);
            }
        }

        else if (valGround.equals(ProtoValue.TRUE) && val.equals(ProtoValue.TRUE)) {

            val = Value.createError(BitWidth.create(Breadboard.PORT_WIDTH));

            if (data == null) {
                state.setData(new InstanceDataSingleton(val));
            } else {
                data.setValue(val);
            }
        }
        else if (valGround.equals(ProtoValue.NOT_CONNECTED)
                || valGround.toString().equals("0")) {

            val = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), 0);
            if (data == null) {
                state.setData(new InstanceDataSingleton(val));
            } else {
                data.setValue(val);
            }
        }
    }
}
