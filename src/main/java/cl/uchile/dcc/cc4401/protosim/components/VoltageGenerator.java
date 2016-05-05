package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.data.Voltage;
import com.cburch.logisim.instance.Instance;
import com.cburch.logisim.instance.InstanceComponent;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.io.Io;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class VoltageGenerator extends InstanceFactory {

    public static InstanceFactory FACTORY = new VoltageGenerator();

    private List<Port> ports;

    public VoltageGenerator() {
        super("VoltageGenerator");
        setIconName("protosimComponentBattery.svg");

        setAttributes(new Attribute[] {
                        Io.ATTR_COMPONENT_ID,
                        StdAttr.LABEL,
                        Io.ATTR_VOLTAGE
                },
                new Object[] {
                        null,
                        "",
                        Voltage.V5
                }
        );

        ports = new ArrayList<Port>();
        
        ports.add(new Port(30, 20, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(30, 10, Port.OUTPUT, Breadboard.PORT_WIDTH));
        
        setPorts(ports);
        setInstanceLogger(com.cburch.logisim.std.io.Led.Logger.class);
    }

    @Override
    public String getDisplayName() {
        return "Voltage Generator";
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
            System.out.println("New voltage generator added with ID "+cid);
        }
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == Io.ATTR_VOLTAGE ) {
            Voltage vol = ((Voltage) instance.getAttributeSet().getValue(attr));

            instance.recomputeBounds();
            //computeTextField(instance);
        }
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        // Chip
        g.setColor(new Color(170, 126, 57));
        g.fillRect(x - 2, y + 5, 25, 20);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x - 2, y + 5, 18, 20);

        // Text
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 8));
        g.drawString("+", x + 17, y + 13);
        g.drawString("-", x + 17, y + 22);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x + 23, y + 8, 6, 4);
        g.fillRect(x + 23, y + 18, 6, 4);

        painter.drawPorts();
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(-2, 2, 32, 25);
    }

    @Override
    public void propagate(InstanceState state) {
        Integer cid = state.getInstance().getComponentId();
        Value o = ProtoValue.TRUE;
        state.setPort(0,o,Breadboard.DELAY);
        state.setPort(1, ProtoValue.FALSE, Breadboard.DELAY);
    }
    
}
