package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionGraph {

    ArrayList<ComponentConnection> connections;
    HashMap<Integer,ArrayList<ComponentConnection>> cFroms;
    HashMap<Integer,ArrayList<ComponentConnection>> cTos;

    public ConnectionGraph(ArrayList<ComponentConnection> c){
        connections = c;
        computeHashTables();
    }

    private void computeHashTables() {
        cFroms = new HashMap<>();
        cTos = new HashMap<>();
        for(ComponentConnection cc : connections){
            int f = cc.getFrom().getId();
            int t = cc.getTo().getId();
            if(cFroms.containsKey(f))
                cFroms.get(f).add(cc);
            else {
                ArrayList<ComponentConnection> lf = new ArrayList<>();
                lf.add(cc);
                cFroms.put(f, lf);
            }
            if(cTos.containsKey(t))
                cTos.get(t).add(cc);
            else {
                ArrayList<ComponentConnection> lt = new ArrayList<>();
                lt.add(cc);
                cTos.put(t, lt);
            }
        }
    }

    public ArrayList<ComponentConnection> getFromConnections(int id){
        return cFroms.get(id);
    }

    public ArrayList<ComponentConnection> getToConnections(int id){
        return cTos.get(id);
    }


}
