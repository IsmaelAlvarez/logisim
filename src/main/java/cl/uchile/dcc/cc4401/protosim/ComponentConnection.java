package cl.uchile.dcc.cc4401.protosim;

/**
 * Created by sergio on 28-04-16.
 */
public class ComponentConnection {

    private int fromId;
    private int toId;

    public ComponentConnection(int f, int t){
        fromId = f;
        toId = t;
    }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }
}
