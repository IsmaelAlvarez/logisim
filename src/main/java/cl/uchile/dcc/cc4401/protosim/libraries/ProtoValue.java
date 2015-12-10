package cl.uchile.dcc.cc4401.protosim.libraries;

import cl.uchile.dcc.cc4401.protosim.components.Breadboard;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Value;

public class ProtoValue {

	private static final int intTrue = -1;
	private static final int intFalse = 0;

	public static final Value TRUE = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), intTrue);
	public static final Value FALSE = Value.createKnown(BitWidth.create(Breadboard.PORT_WIDTH), intFalse);
	public static final Value UNKNOWN = Value.createError(BitWidth.create(Breadboard.PORT_WIDTH));
	public static final Value NOT_CONNECTED = Value.createUnknown(BitWidth.create(Breadboard.PORT_WIDTH));

	/**
	 * Transforma un entero que representa un voltaje, a nuestro valor
	 * representado en booleano. Esto se fundamenta en que el valor del voltage
	 * utiliza la convención Signed Int, para representar enteros. Entonces,
	 * cualquier valor de 32 bits cuya cifra mas significativa sea 1, será
	 * representado por un entero negativo. % volts son 32 unos, es decir -1 en
	 * signed int.
	 * 
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
		if (voltage.equals(UNKNOWN) || voltage.equals(NOT_CONNECTED)) {
			return false;
		} else if (voltage.toIntValue() >= 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEnergized(Value valueVcc, Value valueGround) {
		return !valueVcc.isUnknown() && !valueGround.isUnknown() && valueVcc.equals(ProtoValue.TRUE)
				&& valueGround.equals(ProtoValue.FALSE);
	}
}
