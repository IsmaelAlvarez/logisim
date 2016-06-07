/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.std.io;

import static com.cburch.logisim.util.LocaleString.getFromLocale;

import java.awt.Color;
import java.util.List;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeOption;
import com.cburch.logisim.data.Attributes;
import com.cburch.logisim.data.Capacitance;
import com.cburch.logisim.data.CapacitanceMultiplier;
import com.cburch.logisim.data.ComponentStatus;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Resistance;
import com.cburch.logisim.data.ResistanceMultiplier;
import com.cburch.logisim.data.Voltage;
import com.cburch.logisim.tools.FactoryDescription;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;

public class Io extends Library {
    public static final AttributeOption LABEL_CENTER = new AttributeOption("center", "center", getFromLocale("ioLabelCenter"));

    public static final Attribute<Color> ATTR_COLOR = Attributes.forColor("color",
            getFromLocale("ioColorAttr"));
    public static final Attribute<Color> ATTR_ON_COLOR
        = Attributes.forColor("color", getFromLocale("ioOnColor"));
    public static final Attribute<Color> ATTR_OFF_COLOR
        = Attributes.forColor("offcolor", getFromLocale("ioOffColor"));
    static final Attribute<Color> ATTR_BACKGROUND
        = Attributes.forColor("bg", getFromLocale("ioBackgroundColor"));

    public static final Attribute<Object> ATTR_LABEL_LOC = Attributes.forOption("labelloc",
            getFromLocale("ioLabelLocAttr"),
            new Object[] { LABEL_CENTER, Direction.NORTH, Direction.SOUTH,
                Direction.EAST, Direction.WEST });

    public static final Attribute<Object> ATTR_VOLTAGE = Attributes.forOption("voltage",
            "Voltage",
            new Object[] {Voltage.V4, Voltage.V5, Voltage.V6, Voltage.V7, Voltage.V8, Voltage.V9,
                    Voltage.V10, Voltage.V11, Voltage.V12, Voltage.V13, Voltage.V14, Voltage.V15,
                    Voltage.V16, Voltage.V17, Voltage.V18, Voltage.V19, Voltage.V20});

    public static final Attribute<Object> ATTR_RESISTANCE = Attributes.forOption("resistance",
            "Resistance",
            new Object[] {Resistance.R10,Resistance.R11,Resistance.R12,Resistance.R13,Resistance.R15,Resistance.R16,
                    Resistance.R18,Resistance.R20,Resistance.R22,Resistance.R24,Resistance.R27,Resistance.R30,Resistance.R33,
                    Resistance.R36,Resistance.R39,Resistance.R43,Resistance.R47,Resistance.R51,Resistance.R56,Resistance.R62,
                    Resistance.R68,Resistance.R75,Resistance.R82,Resistance.R91});

    public static final Attribute<Object> ATTR_RESISTANCE_MULTIPLIER = Attributes.forOption("resistance_mult",
            "Resistance Multiplier",
            new Object[] {ResistanceMultiplier.RM1, ResistanceMultiplier.RM10, ResistanceMultiplier.RM100,
                    ResistanceMultiplier.RM1K, ResistanceMultiplier.RM10K, ResistanceMultiplier.RM100K,
                    ResistanceMultiplier.RM1M, ResistanceMultiplier.RM10M, ResistanceMultiplier.RM100M});

    public static final Attribute<Object> ATTR_DIRECTION_LEFT_RIGHT = Attributes.forOption("orientation",
    		"Orientation",
    		new Object[] {Direction.EAST, Direction.WEST});
    
    public static final Attribute<Object> ATTR_CAPACITANCE = Attributes.forOption("capacitance",
            "Capacitance",
            new Object[] {Capacitance.C10,Capacitance.C11,Capacitance.C12,Capacitance.C13,Capacitance.C15,Capacitance.C16,
                    Capacitance.C18,Capacitance.C20,Capacitance.C22,Capacitance.C24,Capacitance.C27,Capacitance.C30,Capacitance.C33,
                    Capacitance.C36,Capacitance.C39,Capacitance.C43,Capacitance.C47,Capacitance.C51,Capacitance.C56,Capacitance.C62,
                    Capacitance.C68,Capacitance.C75,Capacitance.C82,Capacitance.C91});

    public static final Attribute<Object> ATTR_CAPACITANCE_MULTIPLIER = Attributes.forOption("capacitance_mult",
            "Capacitance Multiplier",
            new Object[] {CapacitanceMultiplier.CM1,CapacitanceMultiplier.CM100m,CapacitanceMultiplier.CM10m,CapacitanceMultiplier.CM1m,
                    CapacitanceMultiplier.CM100u,CapacitanceMultiplier.CM10u,CapacitanceMultiplier.CM1u,CapacitanceMultiplier.CM100p,
                    CapacitanceMultiplier.CM10p,CapacitanceMultiplier.CM1p});
    
    public static final Attribute<Object> ATTR_COMPONENT_STATUS = Attributes.forOption("component status",
    		"Component Status",
    		new Object[] {
    				ComponentStatus.GOOD, ComponentStatus.BURNT
    		});
    
    public static final Attribute<Double> ATTR_MAXIMUM_VOLTAGE = Attributes.forDouble("Maximum Voltage");
    
    public static final Attribute<Boolean> ATTR_DISPLAY_CHARGE = Attributes.forBoolean("Display charge");
    public static final Attribute<Double> ATTR_CHARGE = Attributes.forDouble("Charge");
    public static final Attribute<Integer> ATTR_COMPONENT_ID = Attributes.forInteger("ID");

    public static final Attribute<Color> ATTR_LABEL_COLOR = Attributes.forColor("labelcolor",
            getFromLocale("ioLabelColorAttr"));
    public static final Attribute<Boolean> ATTR_ACTIVE = Attributes.forBoolean("active",
            getFromLocale("ioActiveAttr"));

    static final Color DEFAULT_BACKGROUND = new Color(255, 255, 255, 0);

    private static FactoryDescription[] DESCRIPTIONS = {
        new FactoryDescription("Button", getFromLocale("buttonComponent"),
                "button.svg", "Button"),
        new FactoryDescription("Joystick", getFromLocale("joystickComponent"),
                "joystick.svg", "Joystick"),
        new FactoryDescription("Keyboard", getFromLocale("keyboardComponent"),
                "keyboard.svg", "Keyboard"),
        new FactoryDescription("LED", getFromLocale("ledComponent"),
                "led.svg", "Led"),
        new FactoryDescription("7-Segment Display", getFromLocale("sevenSegmentComponent"),
                "7seg.svg", "SevenSegment"),
        new FactoryDescription("14-Segment Display", getFromLocale("fourteenSegmentComponent"),
                "14seg.svg", "FourteenSegment"),
        new FactoryDescription("Hex Digit Display", getFromLocale("hexDigitComponent"),
                "hexdig.svg", "HexDigit"),
        new FactoryDescription("DotMatrix", getFromLocale("dotMatrixComponent"),
                "dotmat.svg", "DotMatrix"),
        new FactoryDescription("TTY", getFromLocale("ttyComponent"),
                "tty.svg", "Tty"),
    };

    private List<Tool> tools = null;

    public Io() { }

    @Override
    public String getName() { return "I/O"; }

    @Override
    public String getDisplayName() { return getFromLocale("ioLibrary"); }

    @Override
    public List<Tool> getTools() {
        if (tools == null) {
            tools = FactoryDescription.getTools(Io.class, DESCRIPTIONS);
        }
        return tools;
    }
}
