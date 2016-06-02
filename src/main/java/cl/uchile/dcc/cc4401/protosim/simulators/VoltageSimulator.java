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

	private HashMap<Integer, ArrayList<AnalogComponent>> connected_to;
	private double current;
	private AnalogComponent vol_gen;
	
	
	@Override
	public void simulate(ArrayList<ComponentConnection> graph) {
		
		vol_gen = AllComponents.getMyInstance().getVoltageGenerator();
		double total_resistance = AllComponents.getMyInstance().calculateEqResistance(vol_gen, vol_gen);
		double voltage = ((Voltage) vol_gen.getAttrs().getValue(Io.ATTR_VOLTAGE)).getVoltage();
		current = voltage / total_resistance;
		
		connected_to = new HashMap<Integer, ArrayList<AnalogComponent>>();
		
		// Genera hasmap id -> lista componentes salida
		for (ComponentConnection cc : graph) {
			if (!connected_to.containsKey(cc.getFrom().getId())) {
				connected_to.put(cc.getFrom().getId(), new ArrayList<AnalogComponent>());
			}
			connected_to.get(cc.getFrom().getId()).add(cc.getTo());
		}
		
		// Inicia la simulacion con la bateria
		start_propagate(voltage);
	}
	
	public void start_propagate(double voltage) {
		
		for (AnalogComponent cc : connected_to.get(vol_gen.getId())) {
			if (cc.getId() == vol_gen.getId())
				continue;
			double output_current = voltage / AllComponents.getMyInstance().calculateEqResistance(cc, vol_gen);
			propagate_voltage(cc, output_current, voltage);
		}
	}
	
	public void propagate_voltage(AnalogComponent comp, double input_current, double input_voltage) {
		
		// Ver si el componente se quema
		comp.checkIfBurns(input_voltage);
		
		// V - I_1 * R_1 = V_2
		// Si comp es la bateria, input_voltage = output_voltage
		double output_voltage = input_voltage - input_current * comp.getRes();
		System.out.println(Integer.toString(comp.getId()) + " voltaje salida: " + Double.toString(output_voltage));
		
		ArrayList<AnalogComponent> to = connected_to.get(comp.getId());
		if (to == null)
			return;
		for (AnalogComponent cc : to) {
			if (cc.getId() == vol_gen.getId())
				continue;
			double output_current = output_voltage / AllComponents.getMyInstance().calculateEqResistance(cc, vol_gen);
			propagate_voltage(cc, output_current, output_voltage);
		}
	}
}
