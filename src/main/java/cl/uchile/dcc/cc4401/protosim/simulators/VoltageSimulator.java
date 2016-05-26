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

	private HashMap<AnalogComponent, ArrayList<AnalogComponent>> connected_to = new HashMap<AnalogComponent, ArrayList<AnalogComponent>>();
	private double current;
	
	
	@Override
	public void simulate(ArrayList<ComponentConnection> graph) {
		
		AnalogComponent vol_gen = AllComponents.getMyInstance().getVoltageGenerator();
		double total_resistance = AllComponents.getMyInstance().calculateEqResistance(vol_gen, vol_gen);
		double voltage = ((Voltage) vol_gen.getAttrs().getValue(Io.ATTR_VOLTAGE)).getVoltage();
		current = voltage / total_resistance;
		
		connected_to.clear();
		
		for (ComponentConnection cc : graph) {
			if (connected_to.containsKey(cc.getFrom())) {
				connected_to.get(cc.getFrom()).add(cc.getTo());
			}
		}
		
		ArrayList<AnalogComponent> components = new ArrayList<AnalogComponent>();
		components.add(vol_gen);
		AnalogComponent component = vol_gen;
		propagate_voltage(vol_gen, voltage);
	}
	
	// Solo funciona en serie
	public void propagate_voltage(AnalogComponent comp, double input) {
		
		comp.checkIfBurns(input);
		double output = input - comp.getRes() * current;
		try {
			for (AnalogComponent cc: connected_to.get(comp)) {
				if (cc != null)
					propagate_voltage(cc, output);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

}
