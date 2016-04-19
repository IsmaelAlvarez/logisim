/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.instance;

import com.cburch.logisim.circuit.CircuitState;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;

import java.awt.*;
import java.util.List;

public class Instance {
    public static Instance getInstanceFor(Component comp) {
        if (comp instanceof InstanceComponent) {
            return ((InstanceComponent) comp).getInstance();
        } else {
            return null;
        }
    }

    private int componentId;
    private int resistance = 0;
    private boolean health_state = true;

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int cmpId){
        componentId = cmpId;
    }

    public void setResistance(int res){
        resistance = res;
    }

    public int getResistance(){
        return resistance;
    }

    public void killComponent(){
        health_state = false;
    }

    public boolean getHealthState(){
        return  health_state;
    }


    public static Component getComponentFor(Instance instance) {
        return instance.comp;
    }

    private InstanceComponent comp;

    Instance(InstanceComponent comp) {
        this.comp = comp;
    }

    InstanceComponent getComponent() {
        return comp;
    }

    public InstanceFactory getFactory() {
        return (InstanceFactory) comp.getFactory();
    }

    public Location getLocation() {
        return comp.getLocation();
    }

    public Bounds getBounds() {
        return comp.getBounds();
    }

    public void setAttributeReadOnly(Attribute<?> attr, boolean value) {
        comp.getAttributeSet().setReadOnly(attr, value);
    }

    public <E> E getAttributeValue(Attribute<E> attr) {
        return comp.getAttributeSet().getValue(attr);
    }

    public void addAttributeListener() {
        comp.addAttributeListener(this);
    }

    public AttributeSet getAttributeSet() {
        return comp.getAttributeSet();
    }

    public List<Port> getPorts() {
        return comp.getPorts();
    }

    public Location getPortLocation(int index) {
        return comp.getEnd(index).getLocation();
    }

    public void setPorts(Port[] ports) {
        comp.setPorts(ports);
    }

    public void recomputeBounds() {
        comp.recomputeBounds();
    }

    public void setTextField(Attribute<String> labelAttr, Attribute<Font> fontAttr,
            int x, int y, int halign, int valign) {
        comp.setTextField(labelAttr, fontAttr, x, y, halign, valign);
    }

    public InstanceData getData(CircuitState state) {
        return (InstanceData) state.getData(comp);
    }

    public void setData(CircuitState state, InstanceData data) {
        state.setData(comp, data);
    }

    public void fireInvalidated() {
        comp.fireInvalidated();
    }
}
