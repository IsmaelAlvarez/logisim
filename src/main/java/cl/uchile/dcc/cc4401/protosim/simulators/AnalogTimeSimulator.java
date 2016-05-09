package cl.uchile.dcc.cc4401.protosim.simulators;

import cl.uchile.dcc.cc4401.protosim.ComponentConnection;

import java.util.ArrayList;

/**
 * Created by sergio on 08-05-16.
 */
public abstract class AnalogTimeSimulator implements AnalogSimulator{

    public abstract void simulateTick(ArrayList<ComponentConnection> graph, double dt);

}
