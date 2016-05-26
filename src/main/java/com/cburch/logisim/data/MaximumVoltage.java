package com.cburch.logisim.data;

public class MaximumVoltage implements AttributeOptionInterface {

	
	private double maximum_voltage;
	
	public void setVoltage(double v) {
		maximum_voltage = v;
	}
	
	public double getVoltage() {
		return (double) getValue();
	}
	
	@Override
	public Object getValue() {

		return maximum_voltage;
	}

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return Double.toString(maximum_voltage);
	}

}
