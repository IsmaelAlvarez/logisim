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

public class NotChip extends AbstractComponent {

  public static InstanceFactory FACTORY = new NotChip();

  private List<Port> ports;

  public NotChip() {
    super("ProtosimNotChip");
    setIconName("protosimComponentChipNot.svg");

    // Set ports
    String[] upperPorts = {Port.INPUT, Port.INPUT, Port.OUTPUT, Port.INPUT,
        Port.OUTPUT, Port.INPUT, Port.OUTPUT};
    String[] lowerPorts = {Port.INPUT, Port.OUTPUT, Port.INPUT, Port.OUTPUT,
        Port.INPUT, Port.OUTPUT, Port.INPUT};
    ports = addPorts(lowerPorts, upperPorts);
    setPorts(ports);
  }

  @Override
  public String getDisplayName() {
    // TODO: l10n this
    // return getFromLocale("andChip");
    return "NOT Chip";
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
    g.drawString("7404 NOT", x + 11, y + 17);
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
  public Bounds getOffsetBounds(AttributeSet attrs) {
    return Bounds.create(0, 0, 60, 30);
  }

  @Override
  public void propagate(InstanceState state) {
    setOutputValue(state,0, 13, 1, 2);
    setOutputValue(state,0, 13, 3, 4);
    setOutputValue(state,0, 13, 5, 6);
    setOutputValue(state,0, 13, 7, 8);
    setOutputValue(state,0, 13, 9, 10);
    setOutputValue(state,0, 13, 11, 12);
  }

  private void setOutputValue(InstanceState state,int vcc, int ground, int portAIndex, int portOutIndex) {
    Value valueVcc = state.getPort(vcc);
    Value valueGround = state.getPort(ground);
    Value valueA = state.getPort(portAIndex);

    Value result = ProtoValue.UNKNOWN;

    if (ProtoValue.isEnergized(valueVcc, valueGround) && ! valueA.equals(ProtoValue.UNKNOWN)) {
      result = ProtoValue.toBoolean(valueA) ? ProtoValue.FALSE : ProtoValue.TRUE;
    }

    state.setPort(portOutIndex, result, Breadboard.DELAY);
  }
}
