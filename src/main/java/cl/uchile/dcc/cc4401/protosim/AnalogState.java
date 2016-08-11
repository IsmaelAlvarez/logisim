package cl.uchile.dcc.cc4401.protosim;


import cl.uchile.dcc.cc4401.protosim.components.AbstractComponent;
import cl.uchile.dcc.cc4401.protosim.simulators.AnalogSimulator;
import cl.uchile.dcc.cc4401.protosim.simulators.AnalogTimeSimulator;
import cl.uchile.dcc.cc4401.protosim.simulators.VoltageSimulator;

import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.EndData;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.proj.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;

public class AnalogState {

    private static AnalogState state = new AnalogState();
    private AnalogTimeSimulator timeSimulator;
    private Timer timer;
    private VoltageSimulator voltageSimulator;

    private AnalogState(){
        timer = new Timer();
    }

    public static AnalogState getInstance() {
        return state;
    }

    //Triggered when Ctrl + W
    public void computeGraph(Project prj){
        Set<Component> comps = prj.getCircuitState().getCircuit().getNonWires();
        Circuit circ = prj.getCircuitState().getCircuit();
        Set<Wire> wires = circ.getWires();
        ArrayList<CompTuple> cons = new ArrayList<>();

        for(Component compi : comps){
            EndData ei = compi.getEnds().get(((AbstractComponent) compi.getFactory()).getVOut());
            Location l1;
            //if(ei.isOutput()) GRAFO
                l1 = ei.getLocation();
            /*else
                l1 = compi.getEnds().get(((AbstractComponent) compi.getFactory()).getVIn()).getLocation();*/
            for(Component compj : comps){
                if(compi==compj) continue;
                EndData ej = compj.getEnds().get(((AbstractComponent) compj.getFactory()).getVIn());
                Location l2;
                //if(ei.isInput())
                    l2 = ej.getLocation();
                /*else
                    l2 = compj.getEnds().get(((AbstractComponent) compi.getFactory()).getVOut()).getLocation();*/
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
        /*
        double res = AllComponents.getMyInstance().calculateEqResistance(AllComponents.getMyInstance().getVoltageGenerator(), AllComponents.getMyInstance().getVoltageGenerator());
        System.out.println(res);
        
        ReductorResistor reductor = new ReductorResistor(AllComponents.getMyInstance().getGraph());
        double resEq = reductor.reduce();
        System.out.println(resEq);*/
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

    public void startSimpleSimulator(AnalogSimulator simulator){
        simulator.simulate(AllComponents.getMyInstance().getGraph());
    }

    public void startTimedSimulator(AnalogTimeSimulator simulator){
        timeSimulator = simulator;
        timeSimulator.simulate(AllComponents.getMyInstance().getGraph());
    }

    public void simulatorTickOnce(float dt){
        if(timeSimulator!=null){
            timeSimulator.simulateTick(AllComponents.getMyInstance().getGraph(),dt);
        }
    }

    public void autoTickSimuator(){
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TickerTask(timeSimulator),0,200);
    }

    public void stopAutoTickSimulator(){
        timer.cancel();
    }
    
    public void simulateVoltage(VoltageSimulator simulator) {
    	voltageSimulator = simulator;
    	simulator.simulate(AllComponents.getMyInstance().getGraph());
    }
    
}
