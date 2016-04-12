package com.cburch.logisim.data;

/**
 * Created by vicenterotman on 4/12/16.
 */
public class ResistanceMultiplier implements AttributeOptionInterface {
    public static final ResistanceMultiplier RM1 = new ResistanceMultiplier(1,"x 1");
    public static final ResistanceMultiplier RM10 = new ResistanceMultiplier(10,"x 10");
    public static final ResistanceMultiplier RM100 = new ResistanceMultiplier(100,"x 100");
    public static final ResistanceMultiplier RM1K = new ResistanceMultiplier(1000,"x 1k");
    public static final ResistanceMultiplier RM10K = new ResistanceMultiplier(10000,"x 10k");
    public static final ResistanceMultiplier RM100K = new ResistanceMultiplier(100000,"x 100k");
    public static final ResistanceMultiplier RM1M = new ResistanceMultiplier(1000000,"x 1M");
    public static final ResistanceMultiplier RM10M = new ResistanceMultiplier(10000000,"x 10M");
    public static final ResistanceMultiplier RM100M = new ResistanceMultiplier(100000000,"x 100M");


    private int value;
    private String name;

    private ResistanceMultiplier(int value, String name) {
        this.value = value;
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

    @Override
    public int hashCode() {
        return value;
    }

    public boolean equals( ResistanceMultiplier other ) {
        if (other != null) {
            return this.value == other.value;
        }
        return false;
    }

    public int getMultiplier(){
        return value;
    }


    // for AttributeOptionInterface
    @Override
    public Object getValue() {
        return this;
    }
}