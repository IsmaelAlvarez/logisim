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
public class CounterChip extends AbstractComponent {

  public static InstanceFactory FACTORY = new CounterChip();

  private List<Port> ports;

  private Attribute<AttributeOption> triggerAttribute;

  public CounterChip() {
    super("CounterChip");
    // TODO Agregar un icono svg especifico para protosimCounterChip
    setIcon(Icons.getIcon("protosimComponentClock.svg"));
    triggerAttribute = StdAttr.TRIGGER;

    // Set ports
    ports = new ArrayList<Port>();

    // Upper ports
    ports.add(new Port(0, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // vcc
    ports.add(new Port(10, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // a
    ports.add(new Port(20, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // b
    ports.add(new Port(30, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // c
    ports.add(new Port(40, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // d
    ports.add(new Port(50, 0, Port.INPUT, Breadboard.PORT_WIDTH)); // not_load

    // Lower ports
    ports.add(new Port(0, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // clk
    ports.add(new Port(10, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_a
    ports.add(new Port(20, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_b
    ports.add(new Port(30, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_c
    ports.add(new Port(40, 30, Port.OUTPUT, Breadboard.PORT_WIDTH)); // q_d
    ports.add(new Port(50, 30, Port.INPUT, Breadboard.PORT_WIDTH)); // gnd

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
    return Bounds.create(0, 0, 50, 30);
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
    g.fillRect(x - 2, y + 5, 54, 20);

    // Text
    g.setColor(Color.white);
    g.setFont(new Font("Courier", Font.BOLD, 8));
    g.drawString("COUNTER", x + 7, y + 13);
    g.drawString("74190", x + 13, y + 22);

    g.drawString("+", x - 1, y + 12);
    g.drawString("-", x + 47, y + 24);

    // Pins
    g.setColor(Color.gray);
    g.fillRect(x - 2, y, 4, 5);
    g.fillRect(x + 8, y, 4, 5);
    g.fillRect(x + 18, y, 4, 5);
    g.fillRect(x + 28, y, 4, 5);
    g.fillRect(x + 38, y, 4, 5);
    g.fillRect(x + 48, y, 4, 5);

    g.fillRect(x - 2, y + 25, 4, 5);
    g.fillRect(x + 8, y + 25, 4, 5);
    g.fillRect(x + 18, y + 25, 4, 5);
    g.fillRect(x + 28, y + 25, 4, 5);
    g.fillRect(x + 38, y + 25, 4, 5);
    g.fillRect(x + 48, y + 25, 4, 5);
    painter.drawPorts();
  }

  @Override
  public void propagate(InstanceState state) {
    StateData data = (StateData) state.getData();
    if (data == null) {
      data = new StateData();
      state.setData(data);
    }
    data.setMod(10);
    
    Object triggerType = state.getAttributeValue(triggerAttribute);
    boolean triggered = data.updateClock(1, state.getPort(6), triggerType);

    Value valueVcc = state.getPort(0);
    Value valueGround = state.getPort(11);

    int[] ports_in = {1, 2, 3, 4};
    int[] ports_out = {7, 8, 9, 10};

    if (ProtoValue.isEnergized(valueVcc, valueGround)) {
      setOutputValue(state, data, triggered, 5, ports_in, ports_out);
    } 
    else {
      for (int i = 0; i < ports_out.length; i++) {
        state.setPort(ports_out[i], ProtoValue.UNKNOWN, Breadboard.DELAY);
      }
    }
  }

  private void setOutputValue(InstanceState state, StateData data, boolean triggered, int not_load, 
      int[] ports_in, int[] ports_out) {
    //load requested
    if (state.getPort(not_load) == ProtoValue.FALSE) {
      String binaryInput = "";
      for (int portIndex: ports_in) {
        binaryInput += value2Bin(state.getPort(portIndex));
      }
      data.setCounter(binaryInput);
    }
    else if (triggered) {
      data.incCounter();
    }

    String stringCounter = data.getCounter();
    for (int i = 0; i < ports_out.length; i++) {
      Value output = bin2Value(stringCounter.charAt(i));
      state.setPort(ports_out[i], output, Memory.DELAY);
    }
  }

  private Value bin2Value(char c) {
    if (c == '1') return ProtoValue.TRUE;
    else return ProtoValue.FALSE;
  }

  private char value2Bin(Value val) {
    if (val == ProtoValue.TRUE) return '1';
    else return '0';
  }

  private static class StateData extends ProtosimClockState implements InstanceData {
    private int counter = 0;
    private int mod = 2;
    private final int bits = 4;

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

    public void setMod(int newMod) {
      mod = newMod;
    }
  }
}