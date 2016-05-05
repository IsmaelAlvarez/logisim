package com.cburch.logisim.data;

/**
 * Created by sergio on 26-04-16.
 */
public class Capacitance implements AttributeOptionInterface {

    public static final Capacitance C10 = new Capacitance(10);
    public static final Capacitance C11 = new Capacitance(11);
    public static final Capacitance C12 = new Capacitance(12);
    public static final Capacitance C13 = new Capacitance(13);
    public static final Capacitance C15 = new Capacitance(15);
    public static final Capacitance C16 = new Capacitance(16);
    public static final Capacitance C18 = new Capacitance(18);
    public static final Capacitance C20 = new Capacitance(20);
    public static final Capacitance C22 = new Capacitance(22);
    public static final Capacitance C24 = new Capacitance(24);
    public static final Capacitance C27 = new Capacitance(27);
    public static final Capacitance C30 = new Capacitance(30);
    public static final Capacitance C33 = new Capacitance(33);
    public static final Capacitance C36 = new Capacitance(36);
    public static final Capacitance C39 = new Capacitance(39);
    public static final Capacitance C43 = new Capacitance(43);
    public static final Capacitance C47 = new Capacitance(47);
    public static final Capacitance C51 = new Capacitance(51);
    public static final Capacitance C56 = new Capacitance(56);
    public static final Capacitance C62 = new Capacitance(62);
    public static final Capacitance C68 = new Capacitance(68);
    public static final Capacitance C75 = new Capacitance(75);
    public static final Capacitance C82 = new Capacitance(82);
    public static final Capacitance C91 = new Capacitance(91);

    private int value;

    private Capacitance(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " F";
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

    public boolean equals( Capacitance other ) {
        return other != null && this.value == other.value;
    }

    public int getCapacitance(){
        return value;
    }

    // for AttributeOptionInterface
    @Override
    public Object getValue() {
        return this;
    }
}
