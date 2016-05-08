package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceDataSingleton;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.tools.Tool;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.Icons;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

import com.cburch.logisim.gui.generic.ZoomControl;
import com.cburch.logisim.gui.main.Frame;

import java.awt.Cursor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Icon;

import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitEvent;
import com.cburch.logisim.circuit.CircuitListener;
import com.cburch.logisim.circuit.RadixOption;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.circuit.WireSet;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentDrawContext;
import com.cburch.logisim.comp.ComponentUserEvent;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.gui.main.Canvas;
import com.cburch.logisim.prefs.AppPreferences;
import com.cburch.logisim.proj.Project;
import com.cburch.logisim.util.Icons;

public class Zoom extends Tool {
	private static final Icon toolIcon=null;// = Icons.getIcon("poke.svg");
    private static final Color caretColor = new Color(255, 255, 150);
    
    private static Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    
    private class Listener implements CircuitListener {

		@Override
		public void circuitChanged(CircuitEvent event) {
			// TODO Auto-generated method stub
			
		}
    }
    
    private Listener listener;
	
	public Zoom() {
		this.listener = new Listener();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void mousePressed(Canvas canvas, Graphics g, MouseEvent e) {
		this.zoomIn();
	}
	
	 @Override
	 public void paintIcon(ComponentDrawContext c, int x, int y) {
	        Graphics g = c.getGraphics();
	        if (toolIcon != null) {
	            toolIcon.paintIcon(c.getDestination(), g, x + 2, y + 2);
	        } else {
	            g.setColor(java.awt.Color.black);
	            /*
	             * The code below draws two arrows. It's only executed if the icon of the PokeTool is not available.
	             * 1st segment is arrow down.
	             * L1 Line Up Down |
	             * L2 Line Mid Left Mid Down \
	             * L3 Line Mid Right Mid Down /
	             * 2nd segment is arrow up.
	             * L1 Line Up Down |
	             * L2 Line Mid Left Mid Up /
	             * L3 Line Mid Right Mid Up \ 
	             */
	            g.drawLine(x + 4, y +  2, x + 4, y + 17);
	            g.drawLine(x + 4, y + 17, x + 1, y + 11);
	            g.drawLine(x + 4, y + 17, x + 7, y + 11);

	            g.drawLine(x + 15, y +  2, x + 15, y + 17);
	            g.drawLine(x + 15, y +  2, x + 12, y + 8);
	            g.drawLine(x + 15, y +  2, x + 18, y + 8);
	        }
	}
	 
	 @Override
	public Cursor getCursor() {
	    return cursor;
	}
	
	public void zoomIn(){
		ZoomControl.spinnerModel.setValue(ZoomControl.spinnerModel.getNextValue());
	}
	
	public void zoomOut(){
		ZoomControl.spinnerModel.setValue(ZoomControl.spinnerModel.getPreviousValue());
	}
}