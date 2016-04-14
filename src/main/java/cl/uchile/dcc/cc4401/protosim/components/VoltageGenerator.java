package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VoltageGenerator extends InstanceFactory {

    public static InstanceFactory FACTORY = new VoltageGenerator();

    private List<Port> ports;

    public VoltageGenerator() {
        super("VoltageGenerator");
        setIconName("protosimComponentBattery.svg");

        ports = new ArrayList<Port>();
        
        ports.add(new Port(30, 10, Port.OUTPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(30, 20, Port.OUTPUT, Breadboard.PORT_WIDTH));
        
        setPorts(ports);
    }

    @Override
    public String getDisplayName() {
        // TODO: l10n this
        // return getFromLocale("andChip");
        return "Voltage Generator";
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        // Chip
        g.setColor(new Color(170, 126, 57));
        g.fillRect(x - 2, y + 5, 25, 20);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x - 2, y + 5, 18, 20);

        // Text
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 8));
        g.drawString("+", x + 17, y + 13);
        g.drawString("-", x + 17, y + 22);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x + 23, y + 8, 6, 4);
        g.fillRect(x + 23, y + 18, 6, 4);

        painter.drawPorts();
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(-2, 2, 32, 25);
    }

    @Override
    public void propagate(InstanceState state) {
        setOutputValue(state, 0, 1);
    }
    
    private void setOutputValue(InstanceState state, int portAIndex, int portBIndex) {
        state.setPort(portAIndex, ProtoValue.TRUE, Breadboard.DELAY);
        state.setPort(portBIndex, ProtoValue.FALSE, Breadboard.DELAY);
    }
    
}
