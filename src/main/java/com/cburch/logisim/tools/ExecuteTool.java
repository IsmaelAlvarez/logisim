package com.cburch.logisim.tools;

import com.cburch.logisim.comp.ComponentDrawContext;
import com.cburch.logisim.util.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ExecuteTool extends Tool {

    private static final Icon toolIcon = Icons.getIcon("execute.svg");

    public ExecuteTool() {

    }

    @Override
    public String getName() {
        return "Execute tool";
    }

    @Override
    public String getDisplayName() {
        return "executeTool";
    }

    @Override
    public String getDescription() {
        return "Execute Circuit";
    }

    @Override
    public void mousePressed(com.cburch.logisim.gui.main.Canvas canvas, Graphics g, MouseEvent e) {

    }

    @Override
    public void mouseDragged(com.cburch.logisim.gui.main.Canvas canvas, Graphics g, MouseEvent e) {

    }

    @Override
    public void mouseReleased(com.cburch.logisim.gui.main.Canvas canvas, Graphics g, MouseEvent e) {

    }

    @Override
    public void paintIcon(ComponentDrawContext c, int x, int y) {
        Graphics g = c.getGraphics();
        if (toolIcon != null) {
            toolIcon.paintIcon(c.getDestination(), g, x + 2, y + 2);
        } else {
            g.setColor(java.awt.Color.black);
            /*
             * The code below It's only executed if the icon is not available.
             */

        }
    }
}
