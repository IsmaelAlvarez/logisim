package cl.uchile.dcc.cc4401.protosim.simulators;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.AnalogComponent;
import cl.uchile.dcc.cc4401.protosim.ComponentConnection;
import com.cburch.logisim.data.Voltage;
import com.cburch.logisim.std.io.Io;

import java.util.ArrayList;

/**
 * Created by sergio on 05-05-16.
 */
public class RCSimulator extends AnalogTimeSimulator {

    private int voltage;
    private double res, cap;

    private AnalogComponent capacitor = null;

    @Override
    public void simulate(ArrayList<ComponentConnection> graph) {
        AnalogComponent voltageComp = AllComponents.getMyInstance().getVoltageGenerator();
        AnalogComponent resistor = null;
        for (ComponentConnection cc : graph) {
            if(cc.getFrom().getId() == voltageComp.getId()){
                resistor = cc.getTo();
                break;
            }
        }
        if(resistor == null) return;
        for (ComponentConnection cc : graph){
            if(cc.getFrom().getId() == resistor.getId()){
                capacitor = cc.getTo();
                break;
            }
        }
        if(capacitor == null) return;
        voltage = ((Voltage) voltageComp.getAttrs().getValue(Io.ATTR_VOLTAGE)).getVoltage();
        res = resistor.getRes();
        cap = capacitor.getCap();
        capacitor.getAttrs().setReadOnly(Io.ATTR_CHARGE,false);
        capacitor.getAttrs().setValue(Io.ATTR_CHARGE,0.0);
        capacitor.getAttrs().setReadOnly(Io.ATTR_CHARGE,true);
    }


    @Override
    public void simulateTick(ArrayList<ComponentConnection> graph, double dt) {
        if(capacitor!=null){
            double qi = capacitor.getAttrs().getValue(Io.ATTR_CHARGE);
            //Discrete differential equation for RC circuit (in percentage)
            double alpha = dt/(res*cap);
            double q = 1;
            if(alpha<1)
                q = qi + alpha*(1-qi);
            capacitor.getAttrs().setReadOnly(Io.ATTR_CHARGE,false);
            capacitor.getAttrs().setValue(Io.ATTR_CHARGE,q);
            capacitor.getAttrs().setReadOnly(Io.ATTR_CHARGE,true);
        }
    }
}
