package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Graphics;

import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;

public abstract class AbstractComponent extends InstanceFactory {

	public AbstractComponent(String name) {
		super(name);
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
}
