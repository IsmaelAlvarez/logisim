package cl.uchile.dcc.cc4401.protosim;


import com.cburch.logisim.comp.Component;
import com.cburch.logisim.std.io.Io;

import java.util.ArrayList;
import java.util.HashMap;

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

    //Conecciones para reduccion de grafo
    private class SimpleComponenteConnection{
        private int id1;
        private double r1;
        private int id2;
        private double r2;

        public SimpleComponenteConnection(int from, double rfrom, int to, double rto){
            this.id1 = from;
            this.r1 = rfrom;
            this.id2 = to;
            this.r2 = rto;
        }

        public double getFromRes(){
            return r1;
        }
        public int getFromId(){
            return id1;
        }
        public double getToRes(){
            return r2;
        }
        public int getToId(){
            return id2;
        }
    }

    //Calcula la resistencia entre 2 puntos
    public double calculateEqResistance(AnalogComponent c1, AnalogComponent c2){
        return calculateResistanceRecursive(c1,c2);
    }

    //Obtener cantidades de concciones por componenente
    private HashMap<Integer, Integer[]> getConnectionsQuantity(ArrayList<SimpleComponenteConnection> graph){
        HashMap<Integer, Integer[]> dic = new HashMap<Integer, Integer[]>();
        //Recorre el grafo y obtiene las cantidades de from y to de cada componente
        for(SimpleComponenteConnection cc : graph){
            //Sumo 1 al from
            if(dic.containsKey(cc.getFromId()))
                dic.get(cc.getFromId())[0]++;
            else{
                Integer[] i = new Integer[2];
                i[0] = 1;
                i[1] = 0;
                dic.put(cc.getFromId(), i);
            }

            //Sumo 1 al to
            if(dic.containsKey(cc.getToId()))
                dic.get(cc.getToId())[1]++;
            else{
                Integer[] i = new Integer[2];
                i[0] = 0;
                i[1] = 1;
                dic.put(cc.getToId(), i);
            }
        }

        return dic;
    }

    //Reduce el grafo
    private ArrayList<SimpleComponenteConnection> reduceGraph(int c1, ArrayList<SimpleComponenteConnection> graph){
        //Condiucion de salida
        if(graph.size() <= 2)
            return graph;

        HashMap<Integer, Integer[]> dic = getConnectionsQuantity(graph);

        //Recorre dic y va determinando cuales conexiones agregar y cuales juntar
        for(Integer k1  : dic.keySet()){
            //Relacion 1-1
            if((dic.get(k1)[0] == 1) && (dic.get(k1)[1] == 1)){
                for(Integer k2  : dic.keySet()){
                    //Relacion 1-1
                    if((dic.get(k2)[0] == 1) && (dic.get(k2)[1] == 1)){
                        //Es serie
                        for(SimpleComponenteConnection cc : graph){
                            if(((cc.getFromId() == k1) && (cc.getToId() == k2))
                                    || ((cc.getFromId() == k2) && (cc.getToId() == k1))){

                            }
                        }

                    }
                }
            }
        }

        return graph;
    }

    //Limpia el grafo, dejando solo la parte entre los 1 componente y la fuente
    private ArrayList<SimpleComponenteConnection> cleanGraph(AnalogComponent c1){
        ArrayList<SimpleComponenteConnection> newGraph = new ArrayList<SimpleComponenteConnection>();

        //Recorro el grafo
        for(ComponentConnection cc : getGraph()){

            //Si la conección va desde c1 a la fuente no continuo recorriendo y añado al grafo
            if((cc.getFrom().getId() == c1.getId()) && (cc.getTo().getId() == getVoltageGenerator().getId())){
                newGraph.add(new SimpleComponenteConnection(cc.getFrom().getId(),cc.getFrom().getRes(),
                        cc.getTo().getId(),cc.getTo().getRes()));
                return newGraph;
            }

            //Si la conección comienza del nodo c1, agrego la llamada recursiva
            if(cc.getFrom().getId() == c1.getId()){
                newGraph.add(new SimpleComponenteConnection(cc.getFrom().getId(),cc.getFrom().getRes(),
                        cc.getTo().getId(),cc.getTo().getRes()));
                newGraph.addAll(cleanGraph(cc.getTo()));
            }
        }

        return newGraph;
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
