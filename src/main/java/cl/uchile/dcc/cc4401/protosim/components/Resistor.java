package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

public class Resistor extends InstanceFactory {

    public static InstanceFactory FACTORY = new Resistor();

    private List<Port> ports;

    public Resistor() {
        super("Resistor");
        setIconName("protosimComponentResistor.svg");

        ports = new ArrayList<Port>();

        ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(40, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));


        setPorts(ports);
    }

    @Override
    public String getDisplayName() {
        // TODO: l10n this
        // return getFromLocale("Resistance");
        return "Resistor";
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();

        Graphics g = painter.getGraphics();

        g.setColor(new Color(170, 126, 57));
        g.fillRect(x+6, y - 4, 28, 8);
        g.setColor(Color.red);
        g.fillRect(x+10, y-4, 5, 8);
        g.setColor(Color.black);
        g.fillRect(x+18, y-4, 5, 8);
        g.setColor(Color.gray);
        g.fillRect(x, y-2, 6, 4);
        g.fillRect(x+34, y-2, 6, 4);

        paintShortCircuit(painter);

        painter.drawPorts();
    }

    public void paintShortCircuit(InstancePainter painter) {
        Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();
        Graphics g = painter.getGraphics();

        int offx = 15;
        int offy = -5;

        g.setColor(Color.red);
        g.fillPolygon(new int[]{x - 10 + offx, x + 20 + offx , x + 18 + offx, x - 12 + offx},
                new int[]{y + 20 + offy, y - 10 + offy, y - 12 + offy, y + 18 + offy}, 4);
        g.setColor(Color.yellow);
        g.fillPolygon(new int[]{x + 8 + offx, x - 4 + offx, x + 5 + offx, x + 2 + offx, x + 14 + offx, x + 5 + offx},
                new int[]{y - 10 + offy, y + 8 + offy, y + 5 + offy, y + 20 + offy, y + 2 + offy, y + 3 + offy}, 6);
        g.setColor(Color.black);
        g.drawPolygon(new int[]{x + 8 + offx, x - 4 + offx , x + 5 + offx, x + 2 + offx, x + 14 + offx, x + 5 + offx},
                new int[]{y - 10 + offy, y + 8 + offy, y + 5 + offy, y + 20 + offy, y + 2 + offy, y + 3 + offy}, 6);
    }

    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        return Bounds.create(0, -5, 40, 10);
    }

    @Override
    public void propagate(InstanceState state) {
        // TODO Auto-generated method stub
    }
}