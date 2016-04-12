/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.std.io;

import com.cburch.logisim.data.*;
import com.cburch.logisim.tools.FactoryDescription;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;

import java.awt.*;
import java.util.List;

import static com.cburch.logisim.util.LocaleString.getFromLocale;

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
