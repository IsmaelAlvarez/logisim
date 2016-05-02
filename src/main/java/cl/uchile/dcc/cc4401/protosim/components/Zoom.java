package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.util.Icons;

public class Zoom extends InstanceFactory {
	
	public Zoom() {
		super("Zoom");
		// TODO Auto-generated constructor stub
	}

	public static InstanceFactory FACTORY = new Zoom();

	@Override
	public void paintInstance(InstancePainter painter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propagate(InstanceState state) {
		// TODO Auto-generated method stub
		
	}
}