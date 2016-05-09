package cl.uchile.dcc.cc4401.protosim;

/**
 * Created by sergio on 28-04-16.
 */
public class ComponentConnection {

    private AnalogComponent fromId;
    private AnalogComponent toId;

    public ComponentConnection(AnalogComponent f, AnalogComponent t){
        fromId = f;
        toId = t;
    }

    public AnalogComponent getTo() {
        return toId;
    }

    public AnalogComponent getFrom() {
        return fromId;
    }
}
