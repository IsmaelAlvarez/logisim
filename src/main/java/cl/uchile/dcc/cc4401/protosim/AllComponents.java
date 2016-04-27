package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.instance.Instance;

import java.util.ArrayList;

/**
 * Created by vicenterotman on 4/14/16.
 */
public class AllComponents {
    private static AllComponents me;
    private ArrayList<Component> components;
    private static int c = 0;

    private AllComponents(){
        components = new ArrayList<Component>();
    }

    public static AllComponents getMyInstance(){
        if(me == null)
            me = new AllComponents();
        return me;
    }

    public int addComponent(Instance c, int resistance){
        Component component = new Component(components.size(), c, resistance);
        components.add(component);
        return component.id;
    }

    public void connect(int id, boolean b){
        for(Component c : components){
            if(c.id == id){
                c.connect(b);
            }
        }
    }

    public void changeResistance(int id, int res){
        System.out.println("Resistencia de: " + res);
        for(Component c : components){
            if(c.id == id){
                c.changeResistance(res);
            }
        }
    }

    public int getTotalResistance(){
        int totalRes = 0;
        for(Component c : components){
            if(c.isConnected()){
                totalRes += c.getResistance();
            }
        }

        return totalRes;
    }

    public void print(){
        for(Component c : components){
            System.out.println("-------------------------------------");
            System.out.println("name: " + c.component.getFactory().getName());
        }
    }

    public int getNextID() {
        c++;
        return c;
    }
}
