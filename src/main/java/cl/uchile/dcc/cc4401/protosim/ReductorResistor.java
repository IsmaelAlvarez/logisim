package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;

public class ReductorResistor extends AbstractGraphReductor {

    public ReductorResistor(ArrayList<ComponentConnection> componentConnections) {
        super(new ConnectionGraph(componentConnections) {
            @Override
            protected double getValue(AnalogComponent component) {
                return component.getRes();
            }
        });
    }

    @Override
    public double getParallelValue(double v1, double v2) {
        return 1/(1/v1+1/v2);
    }

    @Override
    public double getSerialValue(double v1, double v2) {
        return v1+v2;
    }
}
