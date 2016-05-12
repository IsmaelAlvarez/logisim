package cl.uchile.dcc.cc4401.protosim;

import cl.uchile.dcc.cc4401.protosim.simulators.AnalogTimeSimulator;

import java.util.TimerTask;

public class TickerTask extends TimerTask {

    private AnalogTimeSimulator simulator;

    public TickerTask(AnalogTimeSimulator simulator){
        this.simulator = simulator;
    }

    @Override
    public void run() {
        simulator.simulateTick(AllComponents.getMyInstance().getGraph(),0.2);
    }
}
