package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.Icon;

import com.cburch.logisim.tools.Tool;
import com.cburch.logisim.gui.generic.ZoomControl;
import java.awt.event.MouseEvent;

import com.cburch.logisim.circuit.CircuitEvent;
import com.cburch.logisim.circuit.CircuitListener;
import com.cburch.logisim.comp.ComponentDrawContext;
import com.cburch.logisim.gui.main.Canvas;

public class Zoom extends Tool {
	private static final Icon toolIcon=null;// = Icons.getIcon("poke.svg");
    private static final Color caretColor = new Color(255, 255, 150);
    
    private static Cursor cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    
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
	
	@Override
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
	            g.drawOval(x + 4, y + 4, 9, 9);
	            g.drawLine(x +12, y +12, x+20, y+20);
	            g.drawLine(x +5, y +16, x+10, y+16);
	            g.drawLine(x +8, y +14, x+8, y+20);
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