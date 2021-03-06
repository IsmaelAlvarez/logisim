package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.AllComponents;
import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;
import com.cburch.logisim.std.memory.Memory;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.util.Icons;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa un contador sincrono de modulo 10 sin reset, modelo 74190
 * @author Javier
 */
public class ACounterChip extends AbstractComponent {

  public static InstanceFactory FACTORY = new ACounterChip();

  private List<Port> ports;

  private Attribute<AttributeOption> triggerAttribute;

  public ACounterChip() {
    super("ACounterChip");
    // TODO Agregar un icono svg especifico para protosimCounterChip
    setIcon(Icons.getIcon("protosimComponentClock.svg"));
    triggerAttribute = StdAttr.TRIGGER;

    // Set ports
    ports = new ArrayList<Port>();

    // Upper ports
    ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // vcc
    ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // mr1
    ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // mr2
    ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // cp0
    ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // cp1


    // Lower ports
    ports.add(new Port(0, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_a
    ports.add(new Port(10, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_b
    ports.add(new Port(20, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_c
    ports.add(new Port(30, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_d
    ports.add(new Port(40, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // gnd

    setPorts(ports);

    setAttributes(
        new Attribute[] { Io.ATTR_COMPONENT_ID, StdAttr.FACING, StdAttr.LABEL, Pin.ATTR_LABEL_LOC,
            StdAttr.LABEL_FONT, Io.ATTR_MAXIMUM_VOLTAGE, Io.ATTR_COMPONENT_STATUS, Io.ATTR_RESISTANCE},
        new Object[] { null, Direction.EAST, "", Direction.WEST,
            StdAttr.DEFAULT_LABEL_FONT, 10.0, ComponentStatus.GOOD, Resistance.R10});
    setVInOut(0,9); //Cambiar esto
  }

  /**
   * Crea los bordes de previsualizacion
   */
  @Override
  public Bounds getOffsetBounds(AttributeSet attrs) {
    return Bounds.create(0, 0, 40, 30);
  }

  /**
   * Dibuja la instancia del componente
   */
  @Override
  public void paintInstance(InstancePainter painter) {
    Location loc = painter.getLocation();
    int x = loc.getX();
    int y = loc.getY();

    Graphics g = painter.getGraphics();

    // Chip
    g.setColor(Color.black);
    g.fillRect(x - 2, y + 5, 44, 20);

    // Text
    g.setColor(Color.white);
    g.setFont(new Font("Courier", Font.BOLD, 8));
    g.drawString("ACOUNT", x + 6, y + 13);
    g.drawString("7490", x + 11, y + 22);

    g.drawString("+", x - 1, y + 12);
    g.drawString("-", x + 37, y + 24);

    // Pins
    g.setColor(Color.gray);
    g.fillRect(x - 2, y, 4, 5);
    g.fillRect(x + 8, y, 4, 5);
    g.fillRect(x + 18, y, 4, 5);
    g.fillRect(x + 28, y, 4, 5);
    g.fillRect(x + 38, y, 4, 5);

    g.fillRect(x - 2, y + 25, 4, 5);
    g.fillRect(x + 8, y + 25, 4, 5);
    g.fillRect(x + 18, y + 25, 4, 5);
    g.fillRect(x + 28, y + 25, 4, 5);
    g.fillRect(x + 38, y + 25, 4, 5);
    painter.drawPorts();

    if(painter.getInstance().getAttributeSet().getValue(Io.ATTR_COMPONENT_STATUS).equals(ComponentStatus.BURNT))
      paintShortCircuit(painter);

  }

  @Override
  protected void configureNewInstance(Instance instance) {
    instance.addAttributeListener();
    InstanceComponent component = instance.getInstanceComponent();
    Integer cid = component.getAttributeSet().getValue(Io.ATTR_COMPONENT_ID);
    if(cid==null){
      cid = AllComponents.getMyInstance().getNextID();
      component.getAttributeSet().setValue(Io.ATTR_COMPONENT_ID,cid);
      component.getAttributeSet().setReadOnly(Io.ATTR_COMPONENT_ID,true);
      component.getAttributeSet().setReadOnly(Io.ATTR_COMPONENT_STATUS,true);
      System.out.println("New resistor added with ID "+cid);
    }

    //instance.setPorts(new Port[]{ports.get(0), ports.get(9)});

    /*if (instance.getAttributeSet().getValue(Io.ATTR_DIRECTION_LEFT_RIGHT).equals(Direction.WEST)) {
     instance.setPorts(new Port[]{ports.get(0), ports.get(9)});
    }
    */
  }

  @Override
  public void propagate(InstanceState state) {
    StateData data = (StateData) state.getData();
    if (data == null) {
      data = new StateData();
      state.setData(data);
    }
    
    Object triggerType = state.getAttributeValue(triggerAttribute);
    boolean triggered0 = data.updateClock(1, state.getPort(3), triggerType);
    boolean triggered1 = data.updateClock(2, state.getPort(4), triggerType);
    
    Value valueVcc = state.getPort(0);
    Value valueGround = state.getPort(9);
    
    int[] ports_out = {5, 6, 7, 8};
    
    if (ProtoValue.isEnergized(valueVcc, valueGround)) {
      setOutputValue(state, data, triggered0, triggered1, 1, 2, ports_out);
    } 
    else {
      state.setPort(ports_out[0], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(ports_out[1], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(ports_out[2], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(ports_out[3], ProtoValue.UNKNOWN, Breadboard.DELAY);
    }
  }

  private void setOutputValue(InstanceState state, StateData data, boolean triggered0, 
      boolean triggered1, int mr1, int mr2, int[] ports_out) {
    //load requested
    if ((state.getPort(mr1) == ProtoValue.FALSE) && 
        (state.getPort(mr2) == ProtoValue.FALSE)) {
      data.resetCounter();
    }
    else {
      if (triggered0) data.incCounter(1);
      if (triggered1) data.incCounter(0);
    }
    
    state.setPort(ports_out[0], bin2Value(data.getCounter(0).charAt(0)), Memory.DELAY);
    for (int i = 0; i < ports_out.length - 1; i++) {
      state.setPort(ports_out[i + 1], bin2Value(data.getCounter(1).charAt(i)), Memory.DELAY);
    }
  }
  
  private Value bin2Value(char c) {
    if (c == '1') return ProtoValue.TRUE;
    else return ProtoValue.FALSE;
  }

  private static class StateData extends ProtosimClockState implements InstanceData {
    
    private int[] counters = {0, 0};
    private int[] mods = {2, 5};
    private final int[] bits = {1, 3};
    
    public void incCounter(int i) {
      if (counters[i] > mods[i] - 2) counters[i] = 0;
      else counters[i]++;
    }
    
    public void resetCounter() {
      counters[0] = 0;
      counters[1] = 0;
    }
    
    public String getCounter(int i) {
      String ans = Integer.toString(counters[i], 2);
      while (ans.length() < bits[i]) {
        ans = "0" + ans;
      }
      return ans;
    }
  }
}