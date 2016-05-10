package com.cburch.logisim.data;

/**
 * Created by vicenterotman on 4/12/16.
 */
public class Resistance implements AttributeOptionInterface {
    public static final Resistance R10 = new Resistance(10);
    public static final Resistance R11 = new Resistance(11);
    public static final Resistance R12 = new Resistance(12);
    public static final Resistance R13 = new Resistance(13);
    public static final Resistance R15 = new Resistance(15);
    public static final Resistance R16 = new Resistance(16);
    public static final Resistance R18 = new Resistance(18);
    public static final Resistance R20 = new Resistance(20);
    public static final Resistance R22 = new Resistance(22);
    public static final Resistance R24 = new Resistance(24);
    public static final Resistance R27 = new Resistance(27);
    public static final Resistance R30 = new Resistance(30);
    public static final Resistance R33 = new Resistance(33);
    public static final Resistance R36 = new Resistance(36);
    public static final Resistance R39 = new Resistance(39);
    public static final Resistance R43 = new Resistance(43);
    public static final Resistance R47 = new Resistance(47);
    public static final Resistance R51 = new Resistance(51);
    public static final Resistance R56 = new Resistance(56);
    public static final Resistance R62 = new Resistance(62);
    public static final Resistance R68 = new Resistance(68);
    public static final Resistance R75 = new Resistance(75);
    public static final Resistance R82 = new Resistance(82);
    public static final Resistance R91 = new Resistance(91);

    private int value;

    private Resistance(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " ohm";
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

    public boolean equals( Resistance other ) {
        if (other != null) {
            return this.value == other.value;
        }
        return false;
    }

    public int getResistance(){
        return value;
    }


    // for AttributeOptionInterface
    @Override
    public Object getValue() {
        return this;
    }
}