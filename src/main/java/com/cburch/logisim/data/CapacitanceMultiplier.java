package com.cburch.logisim.data;

/**
 * Created by sergio on 26-04-16.
 */
public class CapacitanceMultiplier implements AttributeOptionInterface {
    public static final CapacitanceMultiplier CM1 = new CapacitanceMultiplier(1,"x 1");
    public static final CapacitanceMultiplier CM100m = new CapacitanceMultiplier(0.1,"x 100m");
    public static final CapacitanceMultiplier CM10m = new CapacitanceMultiplier(0.01,"x 10m");
    public static final CapacitanceMultiplier CM1m = new CapacitanceMultiplier(0.001,"x 1m");
    public static final CapacitanceMultiplier CM100u = new CapacitanceMultiplier(0.0001,"x 100u");
    public static final CapacitanceMultiplier CM10u = new CapacitanceMultiplier(0.00001,"x 10u");
    public static final CapacitanceMultiplier CM1u = new CapacitanceMultiplier(0.000001,"x 1u");
    public static final CapacitanceMultiplier CM100p = new CapacitanceMultiplier(0.0000001,"x 100p");
    public static final CapacitanceMultiplier CM10p = new CapacitanceMultiplier(0.00000001,"x 10p");
    public static final CapacitanceMultiplier CM1p = new CapacitanceMultiplier(0.000000001,"x 1p");


    private float value;
    private String name;

    private CapacitanceMultiplier(double value, String name) {
        this.value = (float) value;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toDisplayString() {
        return toString();
    }

    public String getDisplayGetter() {
        return toString();
    }

    public boolean equals( CapacitanceMultiplier other ) {
        return other != null && this.value == other.value;
    }

    public float getMultiplier(){
        return value;
    }


    // for AttributeOptionInterface
    @Override
    public Object getValue() {
        return this;
    }
}