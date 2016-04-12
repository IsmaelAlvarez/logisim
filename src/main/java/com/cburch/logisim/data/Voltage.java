package com.cburch.logisim.data;

/**
 * Created by vicenterotman on 4/6/16.
 */
public class Voltage implements AttributeOptionInterface {
    public static final Voltage V5 = new Voltage(5);
    public static final Voltage V6 = new Voltage(6);
    public static final Voltage V7 = new Voltage(7);
    public static final Voltage V8 = new Voltage(8);
    public static final Voltage V9 = new Voltage(9);

    private int value;


    private Voltage(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " Volts";
    }

    @Override
    public String toDisplayString() {
        return toString();
    }

    public String getDisplayGetter() {
        return toString();
    }

    @Override
    public int hashCode() {
        return value;
    }

    public boolean equals( Voltage other ) {
        if (other != null) {
            return this.value == other.value;
        }
        return false;
    }

    public int getVoltage(){
        return value;
    }


    // for AttributeOptionInterface
    @Override
    public Object getValue() {
        return this;
    }
}
