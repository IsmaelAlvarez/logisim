package cl.uchile.dcc.cc4401.protosim.simulators;

import cl.uchile.dcc.cc4401.protosim.ComponentConnection;

import java.util.ArrayList;

public interface AnalogSimulator {

    public void simulate(ArrayList<ComponentConnection> graph);

}
