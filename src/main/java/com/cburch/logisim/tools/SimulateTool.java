package com.cburch.logisim.tools;

import com.cburch.logisim.comp.ComponentDrawContext;
import com.cburch.logisim.util.Icons;

import javax.swing.*;
import java.awt.*;

public class SimulateTool extends Tool {

    private static final Icon toolIcon = Icons.getIcon("simulate.svg");

    @Override
    public String getName() {
        return "Simulate tool";
    }

    @Override
    public String getDisplayName() {
        return "simulateTool";
    }

    @Override
    public String getDescription() {
        return "Simulate Circuit";
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
