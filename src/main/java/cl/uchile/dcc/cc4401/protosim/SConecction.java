package cl.uchile.dcc.cc4401.protosim;

//impakta3
public class SConecction {
    private int idFrom;
    private int idTo;
    private double valFrom;
    private double valTo;

    public SConecction(int ifr, double vfr, int ito, double vto) {
        idFrom = ifr;
        idTo = ito;
        valFrom = vfr;
        valTo = vto;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public double getValFrom() {
        return valFrom;
    }

    public double getValTo() {
        return valTo;
    }

    public void setFrom(int i, double v){
        idFrom = i;
        valFrom = v;
    }

    public void setTo(int i, double v){
        idTo = i;
        valTo = v;
    }

}
