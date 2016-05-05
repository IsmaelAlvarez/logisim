package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.Icons;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 31-03-16.
 */
public class Capacitor extends InstanceFactory {

    public static InstanceFactory FACTORY = new Capacitor();
    public ArrayList<Integer> capacitors = new ArrayList<>();

    List<Port> ports;

    public Capacitor() {
        super("Capacitor");
        setAttributes(new Attribute[] {
                        Io.ATTR_COMPONENT_ID,
                        StdAttr.LABEL,
                        Io.ATTR_CAPACITANCE,
                        Io.ATTR_CAPACITANCE_MULTIPLIER
                },
                new Object[] {
                        null,
                        "",
                        Capacitance.C10,
                        CapacitanceMultiplier.CM1u
                }
        );

        setFacingAttribute(StdAttr.FACING);
        this.setIcon(Icons.getIcon("protosimComponentCapacitor.svg"));
        ports = new ArrayList<Port>();

        // Lower ports
        //for the moment, i put the port width in 1 bit Breadboard.PORT_WIDTH
        //the pin 0 is where our received the voltage, and the pin 1 is ground (ground is 0 always) or our for the moment show a exception
        ports.add(new Port(0, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        setPorts(ports);
        setInstanceLogger(com.cburch.logisim.std.io.Led.Logger.class);
    }

    @Override
    protected void configureNewInstance(Instance instance) {
        instance.addAttributeListener();
        InstanceComponent component = instance.getInstanceComponent();
        Integer cid = component.getAttributeSet().getValue(Io.ATTR_COMPONENT_ID);
        if(cid==null){
            cid = AllComponents.getMyInstance().getNextID();
            component.getAttributeSet().setValue(Io.ATTR_COMPONENT_ID,cid);
            component.getAttributeSet().setReadOnly(Io.ATTR_COMPONENT_ID,true);
            capacitors.add(cid);
            System.out.println("New capacitor added with ID "+cid);
        }
    }



    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {

    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(-6, -20, 20, 40);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();
        InstanceDataSingleton data = (InstanceDataSingleton) painter.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();

        Graphics g = painter.getGraphics();

        // Chip
        g.setColor(Color.black);
        g.fillRect(x - 5, y - 20, 20, 32);
        GraphicsUtil.drawCenteredArc(g, x+5, y+1, 7, 0, 180);
        g.drawLine(x - 2, y + 5, x - 2, y + 1);
        g.drawLine(x + 12, y + 5, x + 12, y + 1);

        g.setColor(Color.gray);
        g.fillRect(x + 10,y-20,4,32);

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
