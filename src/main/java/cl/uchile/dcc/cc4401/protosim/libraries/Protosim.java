package cl.uchile.dcc.cc4401.protosim.libraries;

import cl.uchile.dcc.cc4401.protosim.components.*;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.PokeTool;
import com.cburch.logisim.tools.Tool;

import java.util.Arrays;
import java.util.List;
import cl.uchile.dcc.cc4401.protosim.components.ACounterChip;
import cl.uchile.dcc.cc4401.protosim.components.AndChip;
import cl.uchile.dcc.cc4401.protosim.components.Breadboard;
import cl.uchile.dcc.cc4401.protosim.components.ClockChip;
import cl.uchile.dcc.cc4401.protosim.components.CounterChip;
import cl.uchile.dcc.cc4401.protosim.components.FlipFlopChip;
import cl.uchile.dcc.cc4401.protosim.components.Led;
import cl.uchile.dcc.cc4401.protosim.components.NandChip;
import cl.uchile.dcc.cc4401.protosim.components.NotChip;
import cl.uchile.dcc.cc4401.protosim.components.OrChip;
import cl.uchile.dcc.cc4401.protosim.components.Resistor;
import cl.uchile.dcc.cc4401.protosim.components.Switch;
import cl.uchile.dcc.cc4401.protosim.components.Timer555Chip;
import cl.uchile.dcc.cc4401.protosim.components.VoltageGenerator;
import cl.uchile.dcc.cc4401.protosim.components.Zoom;
import cl.uchile.dcc.cc4401.protosim.components.ZoomIn;
import cl.uchile.dcc.cc4401.protosim.components.ZoomOut;
//clase impactada

/*
 * Protosim components library for Logisim
 * To display it, must be defined in: src/main/resources/logisim/default.templ
 */
public class Protosim extends Library {

    // Available tools
    private static Tool[] TOOLS = {
            new AddTool(Breadboard.FACTORY),
            new AddTool(VoltageGenerator.FACTORY),
            
            new AddTool(Resistor.FACTORY),  
            new AddTool(Led.FACTORY),
            new AddTool(Capacitor.FACTORY),
            new AddTool(Switch.FACTORY),

            new AddTool(ClockChip.FACTORY),
            new AddTool(FlipFlopChip.FACTORY),
            new AddTool(CounterChip.FACTORY),
            new AddTool (ACounterChip.FACTORY),
            new AddTool(Timer555Chip.FACTORY),


            new AddTool(NotChip.FACTORY),
            new AddTool(AndChip.FACTORY),
            new AddTool(OrChip.FACTORY),
            new AddTool(NandChip.FACTORY),
            new PokeTool(),
            new ZoomIn(),
            new ZoomOut(),
    };

    public Protosim() {
    }

    @Override
    public String getName() {
        return "Protosim";
    }

    @Override
    public String getDisplayName() {
        // TODO: l10n this
        // return getFromLocale("protosimLibrary");
        return "Protosim";
    }

    @Override
    public List<Tool> getTools() {
        return Arrays.asList(TOOLS);
    }
}
