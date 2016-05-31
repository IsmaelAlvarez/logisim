package cl.uchile.dcc.cc4401.protosim;


import com.cburch.logisim.comp.Component;
import com.cburch.logisim.std.io.Io;

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

    /**
    //Calculo de resistencia equivalente
     */


    //Calcula la resistencia entre 2 puntos
    public double calculateEqResistance(AnalogComponent c1, AnalogComponent c2){
        return calculateResistanceRecursive(c1,c2);
    }

    //Calcula la resistencia entre 2 puntos
    private double calculateResistanceRecursive(AnalogComponent c1, AnalogComponent c2){
        double res = 0;
        int froms = 0;

        for(ComponentConnection cc : getGraph()){
            if(cc.getTo().getId() == c1.getId()) froms++;
        }
        
        for(ComponentConnection cc : getGraph()){
            if(cc.getFrom().getId() == c1.getId()){
                //Si esta conectado a c2 retorno su resistencia
                if(cc.getTo().getId() == c2.getId()) {
                    System.out.println("Componente: " + c1.getId() + ", froms: " + froms);
                    return (c1.getRes() * froms);
                }
                res += (1/calculateResistanceRecursive(cc.getTo(),c2));
            }
        }
        return c1.getRes() + 1/res;
    }

    public AnalogComponent getVoltageGenerator() {
    	
    	AnalogComponent ret = null;
    	for (ComponentConnection cc : getGraph()) {
    		if (cc.getFrom().getAttrs().containsAttribute(Io.ATTR_VOLTAGE)) {
    			ret = cc.getFrom();
    			break;
    		}
    	}
    	
    	return ret;
    	
    }
            
    public void print(){
        for(ComponentConnection connection : connections){
            System.out.println(connection.getFrom().getId()+"->"+connection.getTo().getId());

        }
    }
    /**
     //Fin de calculo de resistencia equivalente
     */
    public int getNextID() {
        c++;
        return c;
    }

    public void connectGraph(Component from, Component to) {
        if(from.getAttributeSet().containsAttribute(Io.ATTR_COMPONENT_ID) && to.getAttributeSet().containsAttribute(Io.ATTR_COMPONENT_ID))
            connections.add(new ComponentConnection(new AnalogComponent(from.getAttributeSet()),
                    new AnalogComponent(to.getAttributeSet())));
    }

    public void resetGraph() {
        connections.clear();
    }
    
    public ArrayList<ComponentConnection> getGraph() {
    	return connections;
    }
}
