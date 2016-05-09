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

public class ZoomIn extends Tool {
	private static final Icon toolIcon=null;// = Icons.getIcon("poke.svg");
    private static final Color caretColor = new Color(255, 255, 150);
    
    private static Cursor cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
    
    
	
	public ZoomIn() {
	}

	@Override
	public String getName() {
		return "Zoom in";
	}

	@Override
	public String getDisplayName() {
		return "Zoom in";
	}

	@Override
	public String getDescription() {
		return "Zooms in the circuit";
	}
	
	public void mousePressed(Canvas canvas, Graphics g, MouseEvent e) {
		this.zoomIn();
	}
	
	 @Override
	 public void paintIcon(ComponentDrawContext c, int x, int y) {
        Graphics g = c.getGraphics();
        g.setColor(java.awt.Color.black);
        g.drawOval(x + 4, y + 4, 9, 9);
        g.drawLine(x +12, y +12, x+20, y+20);
        g.drawLine(x +5, y +16, x+10, y+16);
        g.drawLine(x +8, y +14, x+8, y+20);

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