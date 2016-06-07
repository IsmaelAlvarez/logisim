package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;

public class ReductorCapacitor extends AbstractGraphReductor {

    public ReductorCapacitor(ArrayList<ComponentConnection> componentConnections) {
        super(new ConnectionGraph(componentConnections){
            @Override
            protected double getValue(AnalogComponent component) {
                return component.getCap();
            }
        });
    }

    @Override
    public double getParallelValue(double v1, double v2) {
        return v1+v2;
    }

    @Override
    public double getSerialValue(double v1, double v2) {
        return 1/(1/v1+1/v2);
    }
}
