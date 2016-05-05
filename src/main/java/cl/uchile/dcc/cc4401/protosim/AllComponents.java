package cl.uchile.dcc.cc4401.protosim;

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

    /**
    //Calculo de resistencia equivalente
     */
    public class ComponentConnection {
        Component from;
        Component to;

        public ComponentConnection(Component from, Component to){
            this.from = from;
            this.to = to;
        }
    }
    ArrayList<ComponentConnection> listCC = new ArrayList<ComponentConnection>();

    //Metodo para probar
    public void setCircuit(){
        //SET LIST COMPONENTS CONNECTIONS
        /**
         *         _____R2________R3__
         *     V__|___________________|
         *
         *     R2 -> 20
         *     R3 -> 30   Req -> 50
         listCC.add(new ComponentConnection(new Component(1,0),new Component(2,20)));
         listCC.add(new ComponentConnection(new Component(2,20),new Component(3,30)));
         listCC.add(new ComponentConnection(new Component(3,30),new Component(1,0))); */

        /**                     ___R3__
         *         _____R2_____|___R4__|
         *     V__|____________________|
         *
         *     R2 -> 20
         *     R3 -> 30   Req = 37.14
         *     R4 -> 40
         listCC.add(new ComponentConnection(new Component(1,0),new Component(2,20)));
         listCC.add(new ComponentConnection(new Component(2,20),new Component(3,30)));
         listCC.add(new ComponentConnection(new Component(2,20),new Component(4,40)));
         listCC.add(new ComponentConnection(new Component(3,30),new Component(1,0)));
         listCC.add(new ComponentConnection(new Component(4,40),new Component(1,0))); */

        /**                             __R5__
         *                      ___R3__|__R6__|
         *         _____R2_____|___R4_________|
         *     V__|___________________________|
         *
         *     R2 -> 20
         *     R3 -> 30   Req = 37.14
         *     R4 -> 40
         *
         listCC.add(new ComponentConnection(new Component(1,0),new Component(2,20)));
         listCC.add(new ComponentConnection(new Component(2,20),new Component(3,30)));
         listCC.add(new ComponentConnection(new Component(2,20),new Component(4,40)));
         listCC.add(new ComponentConnection(new Component(4,40),new Component(1,0)));
         listCC.add(new ComponentConnection(new Component(3,30),new Component(5,50)));
         listCC.add(new ComponentConnection(new Component(3,30),new Component(6,60)));
         listCC.add(new ComponentConnection(new Component(5,50),new Component(1,0)));
         listCC.add(new ComponentConnection(new Component(6,60),new Component(1,0))); */
    }

    //Calcula la resistencia entre 2 puntos
    public double calculateEqResistance(Component c1, Component c2){
        return calculateResistanceRecursive(c1,c2) - c1.res;
    }

    //Calcula la resistencia entre 2 puntos
    private double calculateResistanceRecursive(Component c1, Component c2){
        double res = 0;
        for(ComponentConnection cc : listCC){
            if(cc.from.id == c1.id){
                //Si esta conectado a c2 retorno su resistencia
                if(cc.to.id == c2.id)
                    return c1.res;

                res += (1/calculateResistanceRecursive(cc.to,c2));
            }
        }

        return c1.res + 1/res;
    }

    /**
     //Fin de calculo de resistencia equivalente
     */

}
