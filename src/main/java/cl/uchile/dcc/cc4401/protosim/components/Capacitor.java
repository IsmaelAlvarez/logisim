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

    List<Port> ports;

    public Capacitor() {
        super("Capacitor");
        setAttributes(new Attribute[] {
                        Io.ATTR_COMPONENT_ID,
                        StdAttr.LABEL,
                        Io.ATTR_CAPACITANCE,
                        Io.ATTR_CAPACITANCE_MULTIPLIER,
                        Io.ATTR_DISPLAY_CHARGE,
                        Io.ATTR_CHARGE
                },
                new Object[] {
                        null,
                        "",
                        Capacitance.C10,
                        CapacitanceMultiplier.CM1u,
                        false,
                        0.0
                }
        );

        setFacingAttribute(StdAttr.FACING);
        this.setIcon(Icons.getIcon("protosimComponentCapacitor.svg"));
        ports = new ArrayList<Port>();

        // Lower ports
        //for the moment, i put the port width in 1 bit Breadboard.PORT_WIDTH
        //the pin 0 is where our received the voltage, and the pin 1 is ground (ground is 0 always) or our for the moment show a exception
        ports.add(new Port(0, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 20, Port.OUTPUT, Breadboard.PORT_WIDTH));
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
            component.getAttributeSet().setReadOnly(Io.ATTR_CHARGE,true);
            //AllComponents.getMyInstance().addComponent(instance,100);
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

        // Charge state
        if(painter.getInstance().getAttributeSet().getValue(Io.ATTR_DISPLAY_CHARGE)){
            g.setColor(Color.green);
            int k = (int) (32*painter.getInstance().getAttributeSet().getValue(Io.ATTR_CHARGE));
            g.fillRect(x+17, y - 19 + (32 - k), 4, k);
            g.setColor(Color.black);
            g.drawRect(x+16,y-20,4,32);
        }

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x - 2, y + 11, 4, 9);
        g.fillRect(x + 8, y + 11, 4, 9);

        painter.drawPorts();
    }

    @Override
    public void propagate(InstanceState state) {
        Value in = state.getPort(0);
        Integer cid = state.getInstance().getComponentId();
        if (in.equals(ProtoValue.TRUE)) {
            Value o = getOutputVoltage(state);
            System.out.println(0);
            state.setPort(1, o, Breadboard.DELAY);
        } else if (in.equals(ProtoValue.NOT_CONNECTED)) {
            state.setPort(1, ProtoValue.NOT_CONNECTED, Breadboard.DELAY);
            //allComponents.connect(state.getInstance().getComponentId(), false);
        } else {
            state.setPort(1, ProtoValue.UNKNOWN, Breadboard.DELAY);
            //allComponents.connect(state.getInstance().getComponentId(), false);
        }
    }

    private Value getOutputVoltage(InstanceState state) {
        return Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH),-1);
    }
}
