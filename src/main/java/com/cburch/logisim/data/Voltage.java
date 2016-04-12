package com.cburch.logisim.data;

/**
 * Created by vicenterotman on 4/6/16.
 */
public class Voltage implements AttributeOptionInterface {
    public static final Voltage V4 = new Voltage(4);
    public static final Voltage V5 = new Voltage(5);
    public static final Voltage V6 = new Voltage(6);
    public static final Voltage V7 = new Voltage(7);
    public static final Voltage V8 = new Voltage(8);
    public static final Voltage V9 = new Voltage(9);
    public static final Voltage V10 = new Voltage(10);
    public static final Voltage V11 = new Voltage(11);
    public static final Voltage V12 = new Voltage(12);
    public static final Voltage V13 = new Voltage(13);
    public static final Voltage V14 = new Voltage(14);
    public static final Voltage V15 = new Voltage(15);
    public static final Voltage V16 = new Voltage(16);
    public static final Voltage V17 = new Voltage(17);
    public static final Voltage V18 = new Voltage(18);
    public static final Voltage V19 = new Voltage(19);
    public static final Voltage V20 = new Voltage(20);

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
