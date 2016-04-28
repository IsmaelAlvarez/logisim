package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.instance.Instance;

import java.util.ArrayList;

/**
 * Created by vicenterotman on 4/14/16.
 */
public class AllComponents {
    private static AllComponents me;
    private ArrayList<Component> components;

    private AllComponents(){
        components = new ArrayList<Component>();
    }

    public static AllComponents getMyInstance(){
        if(me == null)
            me = new AllComponents();

        return me;
    }

    public int addComponent(Instance c, int resistance){
        int id = c.getComponentId();
        if(id != -1){
            System.out.println("new component");
            for(Component co : components){
                if(co.id == id){
                    return id;
                }
            }
        }

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

    public void addListener(int id){
        for(Component c : components){
            if(c.id == id){
                c.addListener();
            }
        }
    }

    public boolean isListenerAdded(int id){
        for(Component c : components){
            if(c.id == id){
                return c.isListenerAdded();
            }
        }
        return true;
    }

    public void print(){
        for(Component c : components){
            System.out.println("-------------------------------------");
            System.out.println("name: " + c.component.getFactory().getName());
        }
    }

}
