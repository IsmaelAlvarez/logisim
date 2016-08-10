package cl.uchile.dcc.cc4401.protosim.simulators;

import java.util.ArrayList;
import java.util.HashMap;

import com.cburch.logisim.data.Voltage;
import com.cburch.logisim.std.io.Io;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.AnalogComponent;
import cl.uchile.dcc.cc4401.protosim.ComponentConnection;

public class VoltageSimulator implements AnalogSimulator {

	private HashMap<Integer, FromToComponent> connected_to;
	private double current;
	private AnalogComponent vol_gen;
	public enum Mode {
		execute, simulate
	}
	private Mode mode = Mode.simulate;
	
	public VoltageSimulator(Mode mode) {
		this.mode = mode;
	}
	
	private class FromToComponent {
		
		private AnalogComponent ac;
		private ArrayList<AnalogComponent> in = new ArrayList<AnalogComponent>();
		private ArrayList<AnalogComponent> out = new ArrayList<AnalogComponent>();
		private double current = 0;
		private int counter_in = 0;
		private int current_in = 0;
		
		public FromToComponent(AnalogComponent ac) {
			
			this.ac = ac;
		}
		
		public AnalogComponent getAnalogComponent() {
			
			return ac;
		}
		
		public boolean addIn(AnalogComponent ac) {
			
			boolean resp = in.add(ac);
			if (resp) {
				counter_in++;
			}
			return resp;
		}
		
		public boolean addOut(AnalogComponent ac) {
			
			return out.add(ac);
		}
		
		public ArrayList<AnalogComponent> getIn() {
			
			return in;
		}
		
		public ArrayList<AnalogComponent> getOut() {
			
			return out;
		}
		
		public boolean countArrival(double corriente_entrada) {
			
			current += corriente_entrada;
			current_in++;
			if (current_in == counter_in)
				return true;
			return false;
		}
		
		public double getCurrent() {
			
			return current;
		}
	}
	
	@Override
	public void simulate(ArrayList<ComponentConnection> graph) {
		
		vol_gen = AllComponents.getMyInstance().getVoltageGenerator();
		double total_resistance = AllComponents.getMyInstance().calculateEqResistance(vol_gen, vol_gen);
		double voltage = ((Voltage) vol_gen.getAttrs().getValue(Io.ATTR_VOLTAGE)).getVoltage();
		current = voltage / total_resistance;
		
		connected_to = new HashMap<Integer, FromToComponent>();
		
		// Genera hasmap id -> lista componentes salida
		for (ComponentConnection cc : graph) {
			if (!connected_to.containsKey(cc.getFrom().getId())) {
				connected_to.put(cc.getFrom().getId(), new FromToComponent(cc.getFrom()));
			}
			connected_to.get(cc.getFrom().getId()).addOut(cc.getTo());
			
			if (!connected_to.containsKey(cc.getTo().getId())) {
				connected_to.put(cc.getTo().getId(), new FromToComponent(cc.getTo()));
			}
			connected_to.get(cc.getTo().getId()).addIn(cc.getFrom());
		}
		
		// Inicia la simulacion con la bateria
		start_propagate(voltage);
	}
	
	public void start_propagate(double voltage) {
		
		for (AnalogComponent cc : connected_to.get(vol_gen.getId()).getOut()) {
			if (cc.getId() == vol_gen.getId())
				continue;
			double output_current = voltage / AllComponents.getMyInstance().calculateEqResistance(cc, vol_gen);
			System.out.println(Integer.toString(cc.getId()) + " llega " + Double.toString(output_current));
			propagate_voltage(cc, output_current, voltage);
		}
	}
	
	public void propagate_voltage(AnalogComponent comp, double input_current, double input_voltage) {
		

		// Saca el componente
		FromToComponent componente = connected_to.get(comp.getId());

		// Si faltan en llegar marca y se devuelve
		if (!componente.countArrival(input_current)) {
			return;
		}
		
		double total_current = componente.getCurrent();
		// V - I_1 * R_1 = V_2
		double output_voltage = input_voltage - total_current * comp.getRes();
		
		// Ver si el componente se quema
		comp.checkIfBurns(total_current * comp.getRes(), this.mode);
		
		System.out.println(Integer.toString(comp.getId()) + " voltaje salida: " + Double.toString(output_voltage));
		
		for (AnalogComponent cc : componente.getOut()) {
			if (cc.getId() == vol_gen.getId())
				continue;
			double output_current = output_voltage / AllComponents.getMyInstance().calculateEqResistance(cc, vol_gen);
			System.out.println(Integer.toString(cc.getId()) + " llega " + Double.toString(output_current));
			propagate_voltage(cc, output_current, output_voltage);
		}
	}
}
