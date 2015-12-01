package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

public class FlipFlopChip extends InstanceFactory {

    public static InstanceFactory FACTORY = new FlipFlopChip();

    private List<Port> ports;

    private Value currentDataValue;
    private int lastClockValue;

    public FlipFlopChip() {
        super("FlipFlopChip");
        setIconName("protosimComponentFlipFlop.svg");

        ports = new ArrayList<Port>();

        // Upper ports
        ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(20, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));

        // Lower ports
        ports.add(new Port(0, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(20, 30, Port.OUTPUT, Breadboard.PORT_WIDTH));

        setPorts(ports);

        currentDataValue = ProtoValue.FALSE;
        lastClockValue = ProtoValue.FALSE.toIntValue();
    }

    @Override
    public String getDisplayName() {
        // TODO: l10n this
        // return getFromLocale("andChip");
        return "Flip-Flop Chip";
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        // Chip
        g.setColor(Color.black);
        g.fillRect(x - 2, y + 5, 24, 20);

        // Text
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 8));
        g.drawString("FLIP", x, y + 13);
        g.drawString("FLOP", x, y + 22);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x - 2, y, 4, 5);
        g.fillRect(x + 8, y, 4, 5);
        g.fillRect(x + 18, y, 4, 5);

        g.fillRect(x - 2, y + 25, 4, 5);
        g.fillRect(x + 8, y + 25, 4, 5);
        g.fillRect(x + 18, y + 25, 4, 5);
        
        painter.drawPorts();
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(0, 0, 20, 30);
    }

    @Override
    public void propagate(InstanceState state) {
        setOutputValue(state, 0, 1, 2);
        setOutputValue(state, 3, 4, 5);
    }

    private void setOutputValue(InstanceState state, int dataPortIndex, int clockPortIndex, int outPortIndex) {
        Value newDataValue = state.getPort(dataPortIndex);
        Value currentClockValue = state.getPort(clockPortIndex);

        if ( ! currentClockValue.isUnknown()
                && lastClockValue == ProtoValue.FALSE.toIntValue()
                && currentClockValue.toIntValue() == ProtoValue.TRUE.toIntValue()) {
            currentDataValue = newDataValue;
        }

        if (currentDataValue.isUnknown()) {
            currentDataValue = ProtoValue.FALSE;
        }

        state.setPort(outPortIndex, currentDataValue, Breadboard.DELAY);
    }

}
