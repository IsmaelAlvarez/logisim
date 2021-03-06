package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

public class AndChip extends AbstractComponent {

    public static InstanceFactory FACTORY = new AndChip();

    private List<Port> ports;

    public AndChip() {
        super("ProtosimAndChip");
        setIconName("protosimComponentChipAnd.svg");

        ports = new ArrayList<Port>();

        // Upper ports
        ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(30, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(50, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(60, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));

        // Lower ports
        ports.add(new Port(0, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(10, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(20, 30, Port.OUTPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(30, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(40, 30, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(50, 30, Port.OUTPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(60, 30, Port.INPUT, Breadboard.PORT_WIDTH));

        setPorts(ports);
    }

    @Override
    public String getDisplayName() {
        // TODO: l10n this
        // return getFromLocale("andChip");
        return "AND Chip";
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        // Chip
        g.setColor(Color.black);
        g.fillRect(x - 2, y + 5, 64, 20);

        // Text
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 9));
        g.drawString("7408 AND", x + 11, y + 17);
        g.drawString("+", x - 1, y + 12);
        g.drawString("-", x + 57, y + 24);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x - 2, y, 4, 5);
        g.fillRect(x + 8, y, 4, 5);
        g.fillRect(x + 18, y, 4, 5);
        g.fillRect(x + 28, y, 4, 5);
        g.fillRect(x + 38, y, 4, 5);
        g.fillRect(x + 48, y, 4, 5);
        g.fillRect(x + 58, y, 4, 5);

        g.fillRect(x - 2, y + 25, 4, 5);
        g.fillRect(x + 8, y + 25, 4, 5);
        g.fillRect(x + 18, y + 25, 4, 5);
        g.fillRect(x + 28, y + 25, 4, 5);
        g.fillRect(x + 38, y + 25, 4, 5);
        g.fillRect(x + 48, y + 25, 4, 5);
        g.fillRect(x + 58, y + 25, 4, 5);
        
        painter.drawPorts();
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(0, 0, 60, 30);
    }

    @Override
    public void propagate(InstanceState state) {
        setOutputValue(state, 0, 13, 1, 2, 3);
        setOutputValue(state, 0, 13, 4, 5, 6);
        setOutputValue(state, 0, 13, 7, 8, 9);
        setOutputValue(state, 0, 13, 10, 11, 12);
    }

    private void setOutputValue(InstanceState state, int vcc, int ground, int portAIndex, int portBIndex, int portOutIndex) {
        Value valueVcc = state.getPort(vcc);
        Value valueGround = state.getPort(ground);
        Value valueA = state.getPort(portAIndex);
        Value valueB = state.getPort(portBIndex);
        
        Value result = ProtoValue.UNKNOWN;
        
        if (ProtoValue.isEnergized(valueVcc, valueGround)) {
            if (ProtoValue.toBoolean(valueA) && ProtoValue.toBoolean(valueB)) {
                result = ProtoValue.TRUE;
            } else {
                result = ProtoValue.FALSE;
            }
        }

        state.setPort(portOutIndex, result, Breadboard.DELAY);
    }
}
