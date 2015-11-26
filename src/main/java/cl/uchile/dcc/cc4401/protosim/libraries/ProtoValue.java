package cl.uchile.dcc.cc4401.protosim.libraries;

public class ProtoValue {

    public static final int FALSE = 0;
    public static final int TRUE = -1;

    /**
     * Transforma un entero que representa un voltaje, a nuestro valor representado en booleano.
     * Esto se fundamenta en que el valor del voltage utiliza la convención Signed Int, para representar enteros.
     * Entonces, cualquier valor de 32 bits cuya cifra mas significativa sea 1, será representado por un entero negativo.
     * % volts son 32 unos, es decir -1 en signed int.
     * @param voltage
     * @return true ó false
     */
    public static boolean toBoolean(int voltage) {
        if (voltage >= 0) {
            return false;
        } else {
            return true;
        }
    }
}
