package cl.uchile.dcc.cc4401.protosim.simulators;

import java.util.ArrayList;
import java.util.HashMap;

import com.cburch.logisim.data.Resistance;
import com.cburch.logisim.data.ResistanceMultiplier;
import com.cburch.logisim.data.Voltage;
import com.cburch.logisim.std.io.Io;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.AnalogComponent;
import cl.uchile.dcc.cc4401.protosim.ComponentConnection;

public class VoltageSimulator implements AnalogSimulator {

	private HashMap<AnalogComponent, ArrayList<AnalogComponent>> connected_to;
	private double current;
	
	
	@Override
	public void simulate(ArrayList<ComponentConnection> graph) {
		
		AnalogComponent vol_gen = AllComponents.getMyInstance().getVoltageGenerator();
		double total_resistance = AllComponents.getMyInstance().calculateEqResistance(vol_gen, vol_gen);
		double voltage = ((Voltage) vol_gen.getAttrs().getValue(Io.ATTR_VOLTAGE)).getVoltage();
		current = voltage / total_resistance;
		
		connected_to = new HashMap<AnalogComponent, ArrayList<AnalogComponent>>();
		
		for (ComponentConnection cc : graph) {
			if (!connected_to.containsKey(cc.getFrom())) {
				connected_to.put(cc.getFrom(), new ArrayList<AnalogComponent>());
			}
			connected_to.get(cc.getFrom()).add(cc.getTo());
		}
		
		propagate_voltage(vol_gen, current);
	}
	
	// Solo funciona en serie
	public void propagate_voltage(AnalogComponent comp, double input) {
		
		double voltage = input * comp.getRes();
		comp.checkIfBurns(voltage);
		double output = input - voltage / comp.getRes();
		ArrayList<AnalogComponent> to = connected_to.get(comp);
		if (to == null)
			return;
		for (AnalogComponent cc: to) {
			if (cc != null)
				propagate_voltage(cc, output);
		}
	}
}
