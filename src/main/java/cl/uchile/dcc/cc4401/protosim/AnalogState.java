package cl.uchile.dcc.cc4401.protosim;


import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.EndData;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.proj.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AnalogState {

    public enum AnalogMode {EDIT_MODE, SIMULATION_MODE, EXECUTION_MODE};

    private AnalogMode mode;
    private float time = 0;
    private static AnalogState state = new AnalogState();

    private AnalogState(){
        mode = AnalogMode.EDIT_MODE;
    }

    public static AnalogState getInstance() {
        return state;
    }

    public AnalogMode getMode(){
        return mode;
    }

    public void setMode(AnalogMode am){
        mode = am;
    }

    public float getElapsedTime(){
        return time;
    }

    public void resetTime(){
        time = 0;
    }

    public void tickOnce() {
        time += 1;
        System.out.println("Current time: "+time);
        AllComponents.getMyInstance().print();

    }

    //Triggered when Ctrl + W
    public void computeGraph(Project prj){
        Set<Component> comps = prj.getCircuitState().getCircuit().getNonWires();
        Circuit circ = prj.getCircuitState().getCircuit();
        Set<Wire> wires = circ.getWires();
        ArrayList<CompTuple> cons = new ArrayList<>();

        for(Component compi : comps){
            EndData ei = compi.getEnds().get(0);
            Location l1;
            if(ei.isOutput())
                l1 = ei.getLocation();
            else
                l1 = compi.getEnds().get(1).getLocation();
            for(Component compj : comps){
                if(compi==compj) continue;
                EndData ej = compj.getEnds().get(0);
                Location l2;
                if(ei.isInput())
                    l2 = ej.getLocation();
                else
                    l2 = compj.getEnds().get(1).getLocation();
                if(isWiredConnected(wires,l1,l2)){
                    cons.add(new CompTuple(compi,compj));
                }
            }
        }

        System.out.println("Connections found: "+cons.size());
        AllComponents.getMyInstance().resetGraph();
        for(CompTuple tuple : cons){
            AllComponents.getMyInstance().connectGraph(tuple.from,tuple.to);
        }
        System.out.println("Graph:");
        AllComponents.getMyInstance().print();
        double res = AllComponents.getMyInstance().calculateEqResistance(AllComponents.getMyInstance().getVoltageGenerator(), AllComponents.getMyInstance().getVoltageGenerator());
        System.out.println(res);
    }

    private boolean isWiredConnected(Set<Wire> wires, Location loc1, Location loc2) {
        HashMap<Location,Location> map = new HashMap<>();
        return isWiredConnectedAux(wires,loc1,loc2,map);
    }

    // Memory needs refactoring
    private boolean isWiredConnectedAux(Set<Wire> wires, Location loc1, Location loc2, HashMap<Location,Location> memory){
        if(memory.containsKey(loc1) && memory.get(loc1)==loc2)
            return false;
        memory.put(loc1,loc2);
        for(Wire w : wires){
            Location w1 = w.getEnd1();
            Location w2 = w.getEnd0();
            if(w1.equals(loc1) && w2.equals(loc2) || w2.equals(loc1) && w1.equals(loc2)){
                return true;
            }
            else if(w1.equals(loc1) && isWiredConnectedAux(wires,w2,loc2,memory)){
                return true;
            }
            else if(w2.equals(loc1) && isWiredConnectedAux(wires,w1,loc2,memory)){
                return true;
            }
        }
        return false;
    }

    private class CompTuple {
        public Component from;
        public Component to;
        public CompTuple(Component f, Component t){
            from = f;
            to = t;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof CompTuple) && ((CompTuple) o).from == from && ((CompTuple) o).to == to;
        }
    }
    
    
    public void simulate() {
    	ArrayList<ComponentConnection> graph = AllComponents.getMyInstance().getGraph();
    	// Lista de componentes del circuito
    	ArrayList<AnalogComponent> componentes = new ArrayList<AnalogComponent>();
    	for (ComponentConnection cc : graph) {
    		if (!componentes.contains(cc))
    			componentes.add(cc.getFrom());
    	}
    	
    }
    
}
