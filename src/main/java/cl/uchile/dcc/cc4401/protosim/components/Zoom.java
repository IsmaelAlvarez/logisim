package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceDataSingleton;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.Icons;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

import com.cburch.logisim.gui.generic.ZoomControl;
import com.cburch.logisim.gui.main.Frame;

public class Zoom extends InstanceFactory {
	
	public Zoom() {
		super("Zoom");
		// TODO Auto-generated constructor stub
		this.setIcon(Icons.getIcon("protosimComponentSwitch.svg"));
	}

	public static InstanceFactory FACTORY = new Zoom();

	@Override
	public void paintInstance(InstancePainter painter) {
		// TODO Auto-generated method stub
		// Do Zoom
		//zoomIn();
		//ZoomControl.spinnerModel.setValue(ZoomControl.spinnerModel.getNextValue());
		//ZoomControl.spinnerModel.setValue(ZoomControl.spinnerModel.getPreviousValue());
		//setZoomFactor();
		Location loc = painter.getLocation();
        int x = loc.getX();
        int y = loc.getY();
        InstanceDataSingleton data = (InstanceDataSingleton) painter.getData();
        Value val = data == null ? Value.FALSE : (Value) data.getValue();
     
        Graphics g = painter.getGraphics();
        
        if (painter.getShowState()) { 

            if (val.equals(ProtoValue.TRUE)) {
                g.setColor(Color.green);
            }
            
            else if (val.equals(ProtoValue.FALSE)
                    || val.equals(ProtoValue.NOT_CONNECTED)
                    || val.toString().equals("0")) {
                g.setColor(Color.gray);
            }
            
            else if (val.equals(ProtoValue.UNKNOWN)) {
                g.setColor(Color.red);
            }
            
            g.fillOval(x - 2, y - 6, 14, 16);
            
          if (val.equals(ProtoValue.UNKNOWN)) {
                g.setColor(Color.white);
                g.setFont(new Font("Courier", Font.BOLD, 9));
                g.drawString("E", x + 3, y + 4);
            }
        }

        // Chip
        g.setColor(Color.black);
        g.fillRect(x - 5, y + 5, 20, 6);
        GraphicsUtil.drawCenteredArc(g, x+5, y+1, 7, 0, 180);
        g.drawLine(x - 2, y + 5, x - 2, y + 1);
        g.drawLine(x + 12, y + 5, x + 12, y + 1);
        
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.BOLD, 7));
        g.drawString("+", x - 3, y + 10);
        g.drawString("-", x + 8, y + 10);

        // Pins
        g.setColor(Color.gray);
        g.fillRect(x - 2, y + 11, 4, 9);
        g.fillRect(x + 8, y + 11, 4, 9);

        painter.drawPorts();
		
	}

	@Override
	public void propagate(InstanceState state) {
		// Do nothing
		
	}
}