package cl.uchile.dcc.cc4401.protosim;


public class AnalogState {

    public enum AnalogMode {EDIT_MODE, SIMULATION_MODE, EXECUTION_MODE};

    private AnalogMode mode;
    private float time = 0;
    private static AnalogState state = new AnalogState();

    private AnalogState(){
        mode = AnalogMode.EDIT_MODE;
    }

    public static AnalogState getInstance() {
        return state;
    }

    public AnalogMode getMode(){
        return mode;
    }

    public void setMode(AnalogMode am){
        mode = am;
    }

    public float getElapsedTime(){
        return time;
    }

    public void resetTime(){
        time = 0;
    }

    public void tickOnce() {
        time += 1;
        System.out.println("Current time: "+time);
    }

}
