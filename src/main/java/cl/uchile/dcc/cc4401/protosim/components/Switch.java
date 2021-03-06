package cl.uchile.dcc.cc4401.protosim.components;

import cl.uchile.dcc.cc4401.protosim.libraries.ProtoValue;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.*;
import com.cburch.logisim.instance.*;
import com.cburch.logisim.std.io.Io;
import com.cburch.logisim.util.GraphicsUtil;
import com.cburch.logisim.util.Icons;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Switch extends AbstractComponent {
    
    public static InstanceFactory FACTORY = new Switch();

    private static final int DEPTH = 3;
    private List<Port> ports;

    public Switch() {
        super("Switch");
        this.setIcon(Icons.getIcon("protosimComponentSwitch.svg"));
        
        setAttributes(new Attribute[] {
                StdAttr.FACING,
                Io.ATTR_COLOR,
                StdAttr.LABEL,
                Io.ATTR_LABEL_LOC,
                StdAttr.LABEL_FONT,
                Io.ATTR_LABEL_COLOR
            },
            new Object[] {
                Direction.EAST,
                Color.WHITE,
                "",
                Io.LABEL_CENTER,
                StdAttr.DEFAULT_LABEL_FONT,
                Color.BLACK
            }
        );
            
        setFacingAttribute(StdAttr.FACING);
        setIconName("protosimComponentSwitch.svg");
        
        ports = new ArrayList<Port>();
        ports.add(new Port(-20, 0, Port.INPUT, Breadboard.PORT_WIDTH));
        ports.add(new Port(0, 0, Port.OUTPUT, Breadboard.PORT_WIDTH));
        setPorts(ports); 
        
        setInstancePoker(Poker.class);
        setInstanceLogger(Logger.class);
    }
      
    @Override
    public Bounds getOffsetBounds(AttributeSet attrs) {
        Direction facing = attrs.getValue(StdAttr.FACING);
        return Bounds.create(-20, -10, 20, 20).rotate(Direction.EAST, facing, 0, 0);
    }

    @Override
    protected void configureNewInstance(Instance instance) {
        instance.addAttributeListener();
        computeTextField(instance);
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == StdAttr.FACING) {
            instance.recomputeBounds();
            computeTextField(instance);
        } else if (attr == Io.ATTR_LABEL_LOC) {
            computeTextField(instance);
        }
    }

    private void computeTextField(Instance instance) {
        Direction facing = instance.getAttributeValue(StdAttr.FACING);
        Object labelLoc = instance.getAttributeValue(Io.ATTR_LABEL_LOC);

        Bounds bds = instance.getBounds();
        int x = bds.getX() + bds.getWidth() / 2;
        int y = bds.getY() + bds.getHeight() / 2;
        int halign = GraphicsUtil.H_CENTER;
        int valign = GraphicsUtil.V_CENTER;

        if (labelLoc == Io.LABEL_CENTER) {
            x = bds.getX() + (bds.getWidth() - DEPTH) / 2;
            y = bds.getY() + (bds.getHeight() - DEPTH) / 2;
        } else if (labelLoc == Direction.NORTH) {
            y = bds.getY() - 2;
            valign = GraphicsUtil.V_BOTTOM;
        } else if (labelLoc == Direction.SOUTH) {
            y = bds.getY() + bds.getHeight() + 2;
            valign = GraphicsUtil.V_TOP;
        } else if (labelLoc == Direction.EAST) {
            x = bds.getX() + bds.getWidth() + 2;
            halign = GraphicsUtil.H_LEFT;
        } else if (labelLoc == Direction.WEST) {
            x = bds.getX() - 2;
            halign = GraphicsUtil.H_RIGHT;
        }

        if (labelLoc == facing) {
            if (labelLoc == Direction.NORTH || labelLoc == Direction.SOUTH) {
                x += 2;
                halign = GraphicsUtil.H_LEFT;
            } else {
                y -= 2;
                valign = GraphicsUtil.V_BOTTOM;
            }
        }

        instance.setTextField(StdAttr.LABEL, StdAttr.LABEL_FONT, x, y, halign, valign);
    }

    @Override
    public void propagate(InstanceState state) {
        int portInIndex = 0;
        int portOutIndex = 1;
        Value valueA = state.getPort(portInIndex);
        Value result;
        
        InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
        Value isPressed = data == null ? ProtoValue.FALSE : (Value) data.getValue();
        
        if (ProtoValue.toBoolean(isPressed) && valueA.isFullyDefined())
            result = valueA;
        else
            result = ProtoValue.UNKNOWN;

        state.setPort(portOutIndex, result, Breadboard.DELAY);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Bounds bds = painter.getBounds();
        int x = bds.getX();
        int y = bds.getY();
        int w = bds.getWidth();
        int h = bds.getHeight();

        Value val;
        if (painter.getShowState()) {
            InstanceDataSingleton data = (InstanceDataSingleton) painter.getData();
            val = data == null ? ProtoValue.FALSE : (Value) data.getValue();
        } else {
            val = ProtoValue.FALSE;
        }

        Color color = painter.getAttributeValue(Io.ATTR_COLOR);
        if ( ! painter.shouldDrawColor()) {
            int hue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
            color = new Color(hue, hue, hue);
        }

        Graphics g = painter.getGraphics();
        int depress;
        if (val == ProtoValue.TRUE) {
            x += DEPTH;
            y += DEPTH;
            Object labelLoc = painter.getAttributeValue(Io.ATTR_LABEL_LOC);
            if (labelLoc == Io.LABEL_CENTER
                    || labelLoc == Direction.NORTH
                    || labelLoc == Direction.WEST) {
                depress = DEPTH;
            } else {
                depress = 0;
            }

            Object facing = painter.getAttributeValue(StdAttr.FACING);
            if (facing == Direction.NORTH || facing == Direction.WEST) {
                Location p = painter.getLocation();
                int px = p.getX();
                int py = p.getY();
                GraphicsUtil.switchToWidth(g, Wire.WIDTH);
                g.setColor(Value.TRUE_COLOR);

                if (facing == Direction.NORTH) {
                    g.drawLine(px, py, px, py + 10);
                }
                else {
                    g.drawLine(px, py, px + 10, py);
                }

                GraphicsUtil.switchToWidth(g, 1);
            }

            g.setColor(color);
            g.fillRect(x, y, w - DEPTH, h - DEPTH);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w - DEPTH, h - DEPTH);
        } else {
            depress = 0;
            int[] xp = new int[] { x, x + w - DEPTH, x + w, x + w, x + DEPTH, x };
            int[] yp = new int[] { y, y, y + DEPTH, y + h, y + h, y + h - DEPTH };
            g.setColor(color.darker());
            g.fillPolygon(xp, yp, xp.length);
            g.setColor(color);
            g.fillRect(x, y, w - DEPTH, h - DEPTH);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w - DEPTH, h - DEPTH);
            g.drawLine(x + w - DEPTH, y + h - DEPTH, x + w, y + h);
            g.drawPolygon(xp, yp, xp.length);
        }

        g.translate(depress, depress);
        g.setColor(painter.getAttributeValue(Io.ATTR_LABEL_COLOR));
        painter.drawLabel();
        g.translate(-depress, -depress);

        painter.drawPorts();
    }

    public static class Poker extends InstancePoker {
        @Override
        public void mousePressed(InstanceState state, MouseEvent e) {
            setValue(state, ProtoValue.TRUE);
        }

        @Override
        public void mouseReleased(InstanceState state, MouseEvent e) {
            setValue(state, ProtoValue.FALSE);
        }

        private void setValue(InstanceState state, Value val) {
            InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
            if (data == null) {
                state.setData(new InstanceDataSingleton(val));
            } else {
                data.setValue(val);
            }
            state.getInstance().fireInvalidated();
        }
    }

    public static class Logger extends InstanceLogger {
        @Override
        public String getLogName(InstanceState state, Object option) {
            return state.getAttributeValue(StdAttr.LABEL);
        }

        @Override
        public Value getLogValue(InstanceState state, Object option) {
            InstanceDataSingleton data = (InstanceDataSingleton) state.getData();
            return data == null ? Value.FALSE : (Value) data.getValue();
        }
    }
}
