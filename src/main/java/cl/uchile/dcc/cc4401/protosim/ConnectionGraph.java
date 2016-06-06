package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionGraph {

    ArrayList<SConecction> connections;
    HashMap<Integer,ArrayList<SConecction>> cFroms;
    HashMap<Integer,ArrayList<SConecction>> cTos;
    Double cacheVal;

    public ConnectionGraph(ArrayList<ComponentConnection> c){
        createSConnection(c);
        computeHashTables();
    }

    /*public ConnectionGraph(ArrayList<SConecction> sc, boolean lazy){
        connections = sc;
        if(!lazy)
            computeHashTables();
    }*/

    private void createSConnection(ArrayList<ComponentConnection> c) {
        connections =  new ArrayList<>();
        for(ComponentConnection cc : c){
            connections.add(new SConecction(cc.getFrom().getId(),cc.getFrom().getCap(),cc.getTo().getId(),cc.getTo().getCap()));
        }
        cacheVal = null;
    }

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
