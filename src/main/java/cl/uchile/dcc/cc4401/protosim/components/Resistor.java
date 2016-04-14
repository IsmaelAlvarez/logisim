package cl.uchile.dcc.cc4401.protosim.components;

import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class Resistor extends InstanceFactory {

    public static InstanceFactory FACTORY = new Resistor();

    private int max_voltage = 5;
    private boolean health_state = true;
    
    private List<Port> ports;

    private int resistance = 10;
    private int resistance_mult = 1;
    
    private int current = 10;	// eliminar al sacar la resistencia

    public Resistor() {
        super("Resistor");
        setIconName("protosimComponentResistor.svg");

        setAttributes(new Attribute[] {
                        StdAttr.LABEL,
                        Io.ATTR_RESISTANCE,
                        Io.ATTR_RESISTANCE_MULTIPLIER
                },
                new Object[] {
                        "",
                        Resistance.R10,
                        ResistanceMultiplier.RM1
                }
        );

        ports = new ArrayList<Port>();

        ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(40, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));


        setPorts(ports);
    }

    @Override
    public String getDisplayName() {
        return "Resistor";
    }

    @Override
    protected void configureNewInstance(Instance instance) {
        instance.addAttributeListener();
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == Io.ATTR_RESISTANCE) {
            Resistance res = ((Resistance) instance.getAttributeSet().getValue(attr));
            resistance = res.getResistance();
            instance.recomputeBounds();
        } else if (attr == Io.ATTR_RESISTANCE_MULTIPLIER){
            ResistanceMultiplier rm = ((ResistanceMultiplier) instance.getAttributeSet().getValue(attr));
            resistance_mult = rm.getMultiplier();
            instance.recomputeBounds();
        }
    }


    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        g.setColor(new Color(170, 126, 57));
        g.fillRect(x+6, y - 4, 28, 8);
        g.setColor(Color.red);
        g.fillRect(x+10, y-4, 5, 8);
        g.setColor(Color.black);
        g.fillRect(x+18, y-4, 5, 8);
        g.setColor(Color.gray);
        g.fillRect(x, y-2, 6, 4);
        g.fillRect(x+34, y-2, 6, 4);

        painter.drawPorts();
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(0, -5, 40, 10);
    }

    private Value getInputVoltage(InstanceState state) {
    	int volt = state.getPort(0).getVoltage();
    	if (volt > max_voltage) {
    		health_state = false;
    		System.out.println(this.toString() + " quemado");
    	} else {
    		health_state = true;
    	}
    	return state.getPort(0);
    }
    
    private Value getOutputVoltage(InstanceState state) {
    	Value in = getInputVoltage(state);
    	Value out = ProtoValue.TRUE;
    	if (!health_state)
    		return ProtoValue.NOT_CONNECTED;
    	int new_volt = current * resistance * resistance_mult;
    	out.setVoltage(new_volt);
    	return out;
    }
    
    @Override
    public void propagate(InstanceState state) {
    	if (state.getPort(0).equals(ProtoValue.TRUE))
    		state.setPort(1, getOutputVoltage(state), Breadboard.DELAY);
    }
}