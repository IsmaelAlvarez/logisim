package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class Resistor extends InstanceFactory {

    public static InstanceFactory FACTORY = new Resistor();

    private double max_voltage = 50;
    private AllComponents allComponents = AllComponents.getMyInstance();

    private List<Port> ports;

    private double current = 0.001;	// eliminar al sacar la resistencia

    public Resistor() {
        super("Resistor");
        setIconName("protosimComponentResistor.svg");

        setAttributes(new Attribute[] {
                        Io.ATTR_COMPONENT_ID,
                        StdAttr.LABEL,
                        Io.ATTR_RESISTANCE,
                        Io.ATTR_RESISTANCE_MULTIPLIER,
                        Io.ATTR_DIRECTION_LEFT_RIGHT
                },
                new Object[] {
                        null,
                        "",
                        Resistance.R10,
                        ResistanceMultiplier.RM1,
                        Direction.EAST
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

    /*
     * Configures new instance using the aa
     * @see com.cburch.logisim.instance.InstanceFactory#configureNewInstance(com.cburch.logisim.instance.Instance)
     */
    @Override
    protected void configureNewInstance(Instance instance) {
        instance.addAttributeListener();
        InstanceComponent component = instance.getInstanceComponent();
        Integer cid = component.getAttributeSet().getValue(Io.ATTR_COMPONENT_ID);
        if(cid==null){
            cid = AllComponents.getMyInstance().getNextID();
            component.getAttributeSet().setValue(Io.ATTR_COMPONENT_ID,cid);
            component.getAttributeSet().setReadOnly(Io.ATTR_COMPONENT_ID,true);
            //AllComponents.getMyInstance().addComponent(instance,100);
            System.out.println("New resistor added with ID "+cid);
        }

        if (instance.getAttributeSet().getValue(Io.ATTR_DIRECTION_LEFT_RIGHT).equals(Direction.WEST)) {
            instance.setPorts(new Port[]{ports.get(1), ports.get(0)});
        }
        //instance.setComponentId(allComponents.addComponent(instance, 10));
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == Io.ATTR_RESISTANCE) {
            Resistance res = ((Resistance) instance.getAttributeSet().getValue(attr));
            instance.recomputeBounds();

            ResistanceMultiplier rm = ((ResistanceMultiplier) instance.getAttributeSet().getValue(Io.ATTR_RESISTANCE_MULTIPLIER));

            //Change Resistance in AllComponents
            //allComponents.changeResistance(instance.getComponentId(),res.getResistance()*rm.getMultiplier());
            instance.setResistance(res.getResistance());
        } else if (attr == Io.ATTR_RESISTANCE_MULTIPLIER){
            ResistanceMultiplier rm = ((ResistanceMultiplier) instance.getAttributeSet().getValue(attr));
            instance.recomputeBounds();

            //allComponents.changeResistance(instance.getComponentId(),instance.getResistance()*rm.getMultiplier());
        } else if (attr == Io.ATTR_DIRECTION_LEFT_RIGHT) {
            // Intercambia la posicion de ambos puertos en la lista.
            Direction direction = ((Direction) instance.getAttributeSet().getValue(Io.ATTR_DIRECTION_LEFT_RIGHT));
            if (direction.equals(Direction.EAST)) {
                instance.setPorts(new Port[]{ports.get(0), ports.get(1)});
            } else {
                instance.setPorts(new Port[]{ports.get(1), ports.get(0)});
            }
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
        double volt = state.getPort(0).getVoltage();

        if (volt > max_voltage) {
            state.getInstance().killComponent();
            System.out.println(this.toString() + " quemado");
        }

        return state.getPort(0);
    }

    private Value getOutputVoltage(InstanceState state) {
        Value in = getInputVoltage(state);
        Value out = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), -1);
        if (!state.getInstance().getHealthState())
            return ProtoValue.NOT_CONNECTED;

        ResistanceMultiplier rm = ((ResistanceMultiplier) state.getInstance().getAttributeSet().getValue(Io.ATTR_RESISTANCE_MULTIPLIER));

        double new_volt = current * rm.getMultiplier() * state.getInstance().getResistance();

        out.setVoltage(new_volt);

    	return out;
    }

    @Override
    public void propagate(InstanceState state) {

    	Value in = state.getPort(0); 
    	if (in.equals(ProtoValue.TRUE)) {
    		state.setPort(1, getOutputVoltage(state), Breadboard.DELAY);
    		allComponents.connect(state.getInstance().getComponentId(), true);
    	} else if (in.equals(ProtoValue.NOT_CONNECTED)) {
    		state.setPort(1, ProtoValue.NOT_CONNECTED, Breadboard.DELAY);
    		allComponents.connect(state.getInstance().getComponentId(), false);
    	} else {
    		state.setPort(1, ProtoValue.UNKNOWN, Breadboard.DELAY);
    		allComponents.connect(state.getInstance().getComponentId(), false);
    	}
    }
}