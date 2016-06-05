package cl.uchile.dcc.cc4401.protosim;

import java.util.ArrayList;
import java.util.HashMap;

public class ReductorCapacitor {

    private ConnectionGraph graph;
    private int idc = 1000;

    public ReductorCapacitor(ArrayList<ComponentConnection> componentConnections){
        graph = new ConnectionGraph(componentConnections);
    }

    public double reduce(){
        boolean changed = true;
        while(changed){
            changed = reduceSerial();
            changed = changed || reduceParallel();
        }
        return graph.getFirstVal();
    }

    private boolean reduceSerial(){
        HashMap<Integer,ArrayList<SConecction>> froms = graph.cFroms;
        //HashMap<Integer,ArrayList<ConnectionGraph.SConecction>> tos = graph.cTos;
        for (Integer fid : froms.keySet()) {
            if(froms.get(fid).size()==1){
                SConecction conn = froms.get(fid).get(0);
                ArrayList<SConecction> oth = graph.getToConnections(conn.getIdTo());
                if(oth.size()==1){
                    //Can be reduced!!
                    reduceGraphSerial(conn);
                    return true;
                }
            }
        }
        return false;
    }

    private void reduceGraphSerial(SConecction conn) {
        double valEq = 1/(1/conn.getValFrom()+1/conn.getValTo());
        ArrayList<SConecction> c1To = graph.getToConnections(conn.getIdFrom());
        ArrayList<SConecction> c2From = graph.getFromConnections(conn.getIdTo());
        for(SConecction sc : c1To){
            sc.setTo(idc,valEq);
        }
        for(SConecction sc : c2From){
            sc.setFrom(idc,valEq);
        }
        graph.reduce(conn);
        idc++;
    }

    private boolean reduceParallel(){

        return false;
    }

}
