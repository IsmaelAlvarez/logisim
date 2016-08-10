package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.Icon;

import com.cburch.logisim.tools.Tool;
import com.cburch.logisim.gui.generic.ZoomControl;
import java.awt.event.MouseEvent;

import com.cburch.logisim.comp.ComponentDrawContext;
import com.cburch.logisim.gui.main.Canvas;

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
	
	@Override
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