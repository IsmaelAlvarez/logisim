package cl.uchile.dcc.cc4401.protosim.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;

import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.util.Icons;

public class NandChip extends AbstractComponent {

  public static InstanceFactory FACTORY = new NandChip();

  private List<Port> ports;

  public NandChip() {
    super("NAND Chip");
    this.setIcon(Icons.getIcon("protosimComponentChipNand.svg")); 

    // Set ports
    String[] upperPorts = {Port.INPUT, Port.INPUT, Port.INPUT, Port.OUTPUT, 
        Port.INPUT, Port.INPUT, Port.OUTPUT};
    String[] lowerPorts = {Port.INPUT, Port.INPUT, Port.OUTPUT, Port.INPUT,
        Port.INPUT, Port.OUTPUT, Port.INPUT};
    ports = addPorts(lowerPorts, upperPorts);
    setPorts(ports);
  }

  @Override
  public Bounds getOffsetBounds(AttributeSet attrs) {
    return Bounds.create(0, 0, 60, 30);
  }

  @Override
  public void paintInstance(InstancePainter painter) {
    Location loc = painter.getLocation();
    int x = loc.getX();
    int y = loc.getY();

    Graphics g = painter.getGraphics();

    // Chip
    g.setColor(Color.black);
    g.fillRect(x - 2, y + 5, 64, 20);

    // Text
    g.setColor(Color.white);
    g.setFont(new Font("Courier", Font.BOLD, 9));
    g.drawString("7400 NAND", x + 8, y + 17);
    g.drawString("+", x-1, y + 12);
    g.drawString("-", x + 57, y + 24);

    // Pins
    g.setColor(Color.gray);
    g.fillRect(x - 2, y, 4, 5);
    g.fillRect(x + 8, y, 4, 5);
    g.fillRect(x + 18, y, 4, 5);
    g.fillRect(x + 28, y, 4, 5);
    g.fillRect(x + 38, y, 4, 5);
    g.fillRect(x + 48, y, 4, 5);
    g.fillRect(x + 58, y, 4, 5);

    g.fillRect(x - 2, y + 25, 4, 5);
    g.fillRect(x + 8, y + 25, 4, 5);
    g.fillRect(x + 18, y + 25, 4, 5);
    g.fillRect(x + 28, y + 25, 4, 5);
    g.fillRect(x + 38, y + 25, 4, 5);
    g.fillRect(x + 48, y + 25, 4, 5);
    g.fillRect(x + 58, y + 25, 4, 5);

    painter.drawPorts();
  }

  @Override
  public void propagate(InstanceState state) {
    setOutputValue(state, 0, 13, 1, 2, 3);
    setOutputValue(state, 0, 13, 4, 5, 6);
    setOutputValue(state, 0, 13, 7, 8, 9);
    setOutputValue(state, 0, 13, 10, 11, 12);
  }

  private void setOutputValue(InstanceState state,int vcc, int ground, int portAIndex, int portBIndex, int portOutIndex) {
    Value valueA = state.getPort(portAIndex);
    Value valueB = state.getPort(portBIndex);
    Value valueVcc = state.getPort(vcc);
    Value valueGround = state.getPort(ground);

    Value result = ProtoValue.UNKNOWN;

    if (ProtoValue.isEnergized(valueVcc, valueGround)) {
      if (ProtoValue.toBoolean(valueA) && ProtoValue.toBoolean(valueB)) {
        result = ProtoValue.FALSE;
      } else {
        result = ProtoValue.TRUE;
      }
    }

    state.setPort(portOutIndex, result, Breadboard.DELAY);
  }

}
