package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.circuit.CircuitEvent;
import com.cburch.logisim.circuit.CircuitListener;
import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        instance.setComponentId(allComponents.addComponent(instance, 10));
        System.out.println("configureNewInstance");
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == Io.ATTR_RESISTANCE) {
            Resistance res = ((Resistance) instance.getAttributeSet().getValue(attr));
            instance.recomputeBounds();

            ResistanceMultiplier rm = ((ResistanceMultiplier) instance.getAttributeSet().getValue(Io.ATTR_RESISTANCE_MULTIPLIER));

            //Change Resistance in AllComponents
            allComponents.changeResistance(instance.getComponentId(),res.getResistance()*rm.getMultiplier());
            instance.setResistance(res.getResistance());
        } else if (attr == Io.ATTR_RESISTANCE_MULTIPLIER){
            ResistanceMultiplier rm = ((ResistanceMultiplier) instance.getAttributeSet().getValue(attr));
            instance.recomputeBounds();

            //Change Resistance in AllComponents
            allComponents.changeResistance(instance.getComponentId(),instance.getResistance()*rm.getMultiplier());
        }
    }


    @Override
    public void paintInstance(InstancePainter painter) {
        int compId = painter.getInstance().getComponentId();

        if (!allComponents.isListenerAdded(compId)){
            allComponents.addListener(compId);
            painter.getCircuit().addCircuitListener(new CircuitListener() {
                int lastValue = 0;
                Integer componentId = null;

                @Override
                public void circuitChanged(CircuitEvent event) {

                    if(componentId == null){
                        componentId = compId;
                    }

                    if((event.getAction() == 6) && (lastValue == 2)){
                        //Change connection in AllComponents
                        allComponents.connect(componentId, false);
                    }

                    lastValue = event.getAction();
                }
            });
        }

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
    	Value out = ProtoValue.TRUE;
    	if (!state.getInstance().getHealthState())
    		return ProtoValue.NOT_CONNECTED;

        ResistanceMultiplier rm = ((ResistanceMultiplier) state.getInstance().getAttributeSet().getValue(Io.ATTR_RESISTANCE_MULTIPLIER));

    	double new_volt = current * rm.getMultiplier() * state.getInstance().getResistance();

    	out.setVoltage(new_volt);
    	return out;
    }
    
    @Override
    public void propagate(InstanceState state) {
    	if (state.getPort(0).equals(ProtoValue.TRUE)) {
            state.setPort(1, getOutputVoltage(state), Breadboard.DELAY);

            //Change connection in AllComponents
            allComponents.connect(state.getInstance().getComponentId(), true);
        }else{
            state.setPort(1, ProtoValue.NOT_CONNECTED, Breadboard.DELAY);

            //Change connection in AllComponents
            allComponents.connect(state.getInstance().getComponentId(), false);
        }
    }


}