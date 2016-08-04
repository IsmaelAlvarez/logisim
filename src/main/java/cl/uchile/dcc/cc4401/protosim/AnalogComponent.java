package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.data.*;
import com.cburch.logisim.std.io.Io;

public class AnalogComponent {
    private int id;
    private AttributeSet attrs;

    public AnalogComponent(int id, AttributeSet attrs){
        this.id = id;
        this.attrs = attrs;
    }

    public AnalogComponent(AttributeSet attrs){
        this.id = attrs.getValue(Io.ATTR_COMPONENT_ID);
        this.attrs = attrs;
    }

    public int getId() {
        return id;
    }
    
    public double getRes() {
    	Resistance res = (Resistance) attrs.getValue(Io.ATTR_RESISTANCE);
    	if (res == null) {
    		return 0;
    	}
    	ResistanceMultiplier rm = (ResistanceMultiplier) attrs.getValue(Io.ATTR_RESISTANCE_MULTIPLIER);
        if(rm == null) rm = ResistanceMultiplier.RM1;
    	return res.getResistance() * rm.getMultiplier();
    	
    }

    public AttributeSet getAttrs() {
        return attrs;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof AnalogComponent) && ((AnalogComponent) o).id == this.id;
    }

    public double getCap() {
        Capacitance cap = (Capacitance) attrs.getValue(Io.ATTR_CAPACITANCE);
        if (cap == null) {
            return Double.POSITIVE_INFINITY;
        }
        CapacitanceMultiplier cm = (CapacitanceMultiplier) attrs.getValue(Io.ATTR_CAPACITANCE_MULTIPLIER);
        return cap.getCapacitance() * cm.getMultiplier();
    }
    
    public void checkIfBurns(Double input) {
        System.out.println(input);
        if (attrs.containsAttribute(Io.ATTR_COMPONENT_STATUS)) {
    		if (attrs.containsAttribute(Io.ATTR_MAXIMUM_VOLTAGE)) {
    			Double max = (Double) attrs.getValue(Io.ATTR_MAXIMUM_VOLTAGE);
    			if (input > max) {
                    attrs.setReadOnly(Io.ATTR_COMPONENT_STATUS,false);
    				attrs.setValue(Io.ATTR_COMPONENT_STATUS, ComponentStatus.BURNT);
                    attrs.setReadOnly(Io.ATTR_COMPONENT_STATUS,true);
                }
    		}
    	}
    }
}
