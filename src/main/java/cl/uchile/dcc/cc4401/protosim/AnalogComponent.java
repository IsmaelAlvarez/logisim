package cl.uchile.dcc.cc4401.protosim;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.std.io.Io;

public class AnalogComponent {
    private int id;
    private AttributeSet attrs;

    public AnalogComponent(int id, AttributeSet attrs){
        this.id = id;
        this.attrs = attrs;
    }

    public AnalogComponent(AttributeSet attrs){
        this.id = attrs.getValue(Io.ATTR_COMPONENT_ID);
        this.attrs = attrs;
    }

    public int getId() {
        return id;
    }

    public AttributeSet getAttrs() {
        return attrs;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof AnalogComponent) && ((AnalogComponent) o).id == this.id;
    }
}
