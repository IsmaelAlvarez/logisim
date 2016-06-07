package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ConnectionGraph {

    ArrayList<SConecction> connections;
    HashMap<Integer,ArrayList<SConecction>> cFroms;
    HashMap<Integer,ArrayList<SConecction>> cTos;
    Double cacheVal;

    public ConnectionGraph(ArrayList<ComponentConnection> c){
        createSConnection(c);
        computeHashTables();
    }

    private void createSConnection(ArrayList<ComponentConnection> c) {
        connections =  new ArrayList<>();
        for(ComponentConnection cc : c){
            //connections.add(new SConecction(cc.getFrom().getId(),cc.getFrom().getCap(),cc.getTo().getId(),cc.getTo().getCap()));
            connections.add(new SConecction(cc.getFrom().getId(), getValue(cc.getFrom()), cc.getTo().getId(), getValue(cc.getTo())));
        }
        cacheVal = null;
    }

    protected abstract double getValue(AnalogComponent component);


    private void computeHashTables() {
        cFroms = new HashMap<>();
        cTos = new HashMap<>();
        for(SConecction cc : connections){
            int f = cc.getIdFrom();
            int t = cc.getIdTo();
            if(cFroms.containsKey(f))
                cFroms.get(f).add(cc);
            else {
                ArrayList<SConecction> lf = new ArrayList<>();
                lf.add(cc);
                cFroms.put(f, lf);
            }
            if(cTos.containsKey(t))
                cTos.get(t).add(cc);
            else {
                ArrayList<SConecction> lt = new ArrayList<>();
                lt.add(cc);
                cTos.put(t, lt);
            }
        }
    }

    public ArrayList<SConecction> getFromConnections(int id){
        return cFroms.get(id);
    }

    public ArrayList<SConecction> getToConnections(int id){
        return cTos.get(id);
    }

    public double getFirstVal() {
        return cacheVal;
    }

    public void reduce(SConecction conn) {
        connections.remove(conn);
        if(connections.size()==1)
            cacheVal = connections.get(0).getValTo();
        computeHashTables();
    }

    public void reduce(SConecction conn1, SConecction conn2){
        connections.remove(conn1);
        reduce(conn2);
    }
}
