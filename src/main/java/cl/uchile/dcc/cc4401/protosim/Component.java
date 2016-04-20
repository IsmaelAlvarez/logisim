package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.instance.Instance;

/**
 * Created by vicenterotman on 4/19/16.
 */
public class Component {
    public int id;
    public Instance component;
    private boolean connected;
    private int resistance;

    public Component(int id, Instance component, int resistance){
        this.id = id;
        this.component = component;
        this.connected = false;
        this.resistance = resistance;
    }

    public boolean isConnected(){
        return connected;
    }

    public void connect(boolean b){
        connected = b;
    }

    public void changeResistance(int res){
        resistance = res;
    }

    public int getResistance(){
        return resistance;
    }
}
