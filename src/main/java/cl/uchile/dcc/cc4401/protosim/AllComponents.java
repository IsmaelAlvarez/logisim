package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.comp.Component;

import java.util.ArrayList;

/**
 * Created by vicenterotman on 4/14/16.
 */
public class AllComponents {
    private static AllComponents me;
    private ArrayList<ComponentConnection> connections;
    private static int c = 0;

    private AllComponents(){
        connections =  new ArrayList<>();
    }

    public static AllComponents getMyInstance(){
        if(me == null)
            me = new AllComponents();
        return me;
    }

    public void connect(int id, boolean b){

    }

    public void print(){
        for(ComponentConnection connection : connections){
            System.out.println(connection.getFrom().getId()+"->"+connection.getTo().getId());
        }
    }

    public int getNextID() {
        c++;
        return c;
    }

    public void connectGraph(Component from, Component to) {
        connections.add(new ComponentConnection(new AnalogComponent(from.getAttributeSet()),
                new AnalogComponent(to.getAttributeSet())));
    }

    public void resetGraph() {
        connections.clear();
    }
}
