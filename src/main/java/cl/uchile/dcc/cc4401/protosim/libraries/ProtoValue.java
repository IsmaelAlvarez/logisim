
package cl.uchile.dcc.cc4401.protosim.libraries;

import cl.uchile.dcc.cc4401.protosim.components.Breadboard;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Value;

public class ProtoValue {

	// Private porque solo esta clase debería trabajar con estos valores. El público general trabajará con TRUE y FALSE, que son Value.
	private static final int intTrue = -1;
	private static final int intFalse = 0;
    public static final Value TRUE = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), intTrue);
    public static final Value FALSE = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), intFalse);
    public static String MAX_VOLT_VALUE = "1111 1111 1111 1111 1111 1111 1111 1111";
    public static final Value UNKNOWN = Value.createUnknown(BitWidth.create(Breadboard.PORT_WIDTH));
    public static String MIN_VOLT_VALUE = "0000 0000 0000 0000 0000 0000 0000 0000";
    public static String UNKNOWN_VOLT_VALUE = "xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx";
    public static String ERROR_VOLT_VALUE = "EEEE EEEE EEEE EEEE EEEE EEEE EEEE EEEE";

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
    
    public static boolean toBoolean(Value voltage) {
        if (voltage.toIntValue() >= 0) {
            return false;
        } else {
            return true;
        }
    }
}
