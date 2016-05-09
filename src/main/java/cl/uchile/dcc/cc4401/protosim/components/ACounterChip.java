package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeOption;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.memory.Memory;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.util.Icons;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

/**
 * Implementa un contador sincrono de modulo 10 sin reset, modelo 74190
 * @author Javier
 */
public class ACounterChip extends InstanceFactory {

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
    ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // not_cp0
    ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // not_cp1


    // Lower ports
    ports.add(new Port(0, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_a
    ports.add(new Port(10, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_b
    ports.add(new Port(20, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_c
    ports.add(new Port(30, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_d
    ports.add(new Port(40, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // gnd

    setPorts(ports);

    setAttributes(
        new Attribute[] { StdAttr.FACING, StdAttr.LABEL, Pin.ATTR_LABEL_LOC,
            StdAttr.LABEL_FONT },
        new Object[] { Direction.EAST, "", Direction.WEST,
            StdAttr.DEFAULT_LABEL_FONT });
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
  }



  @Override
  public void propagate(InstanceState state) {
    StateData data = (StateData) state.getData();
    if (data == null) {
      data = new StateData();
      state.setData(data);
    }
    Object triggerType = state.getAttributeValue(triggerAttribute);
    boolean triggered1 = data.updateClock(1, state.getPort(3), triggerType);
    boolean triggered2 = data.updateClock(1, state.getPort(4), triggerType);
    
    Value valueVcc = state.getPort(0);
    Value valueGround = state.getPort(9);
    
    int[] out = {5, 6, 7, 8};
    
    if (ProtoValue.isEnergized(valueVcc, valueGround)) {
      setOutputValue(state, data, triggered1, 0, 1, 2, out);
      setOutputValue(state, data, triggered2, 1, 1, 2, out);
    } 
    else {
      state.setPort(out[0], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(out[1], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(out[2], ProtoValue.UNKNOWN, Breadboard.DELAY);
      state.setPort(out[3], ProtoValue.UNKNOWN, Breadboard.DELAY);
    }
  }

  private void setOutputValue(InstanceState state, StateData data, boolean triggered, int counter, 
      int mr1, int mr2, int[] out) {
    //load requested
    if ((state.getPort(mr1) == ProtoValue.FALSE) && 
        (state.getPort(mr2) == ProtoValue.FALSE)) {
      data.setCounter(counter, "0");
    }
    else if (triggered) {
      data.incCounter(counter);
    }
    
    state.setPort(out[0], bin2Value(data.getCounter(0).charAt(0)), Memory.DELAY);
    for (int i = 1; i < out.length; i++) {
      state.setPort(out[i], bin2Value(data.getCounter(1).charAt(i)), Memory.DELAY);
    }
  }
  
  private Value bin2Value(char c) {
    if (c == '1') return ProtoValue.TRUE;
    else return ProtoValue.FALSE;
  }

  private static class StateData extends ProtosimClockState implements InstanceData {
    private Counter[] counterArray = {new Counter(2), new Counter(5)};
    
    public void incCounter(int counter) {
      counterArray[counter].incCounter();
    }
    
    public void setCounter(int counter, String binaryNumber) {
      counterArray[counter].setCounter(binaryNumber);
    }
    
    public String getCounter(int counter) {
      return counterArray[counter].getCounter();
    }
  }
  
  private static class Counter {
    private int counter = 0;
    private int mod;
    private int bits;
    
    public Counter(int counterMod) {
      mod = counterMod;
      bits = (int) Math.round(Math.log(mod)/Math.log(2));
    }
    
    public void incCounter() {
      if (counter > mod - 2) counter = 0;
      else counter++;
    }
    
    public void setCounter(String binaryNumber) {
      counter = Integer.parseInt(binaryNumber, 2);
    }
    
    public String getCounter() {
      String ans = Integer.toString(counter, 2);
      while (ans.length() < bits) {
        ans = "0" + ans;
      }
      return ans;
    }
  }
}