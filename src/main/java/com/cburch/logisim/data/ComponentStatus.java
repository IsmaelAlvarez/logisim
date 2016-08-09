package com.cburch.logisim.data;

public class ComponentStatus implements AttributeOptionInterface {

	public static final ComponentStatus GOOD = new ComponentStatus(PossibleStatuses.GOOD);
	public static final ComponentStatus BURNT = new ComponentStatus(PossibleStatuses.BURNT);
	public static final ComponentStatus BAD = new ComponentStatus(PossibleStatuses.BAD);
	
	private static enum PossibleStatuses {
			GOOD, BURNT, BAD
	}
	
	PossibleStatuses status;
	
	private ComponentStatus() {
		
		status = PossibleStatuses.GOOD;
	}
	
	private ComponentStatus(PossibleStatuses status) {
		
		this.status = status;  
	}
	
	@Override
	public Object getValue() {
		
		return status;
	}

	public boolean equals(Object obj) {
		
		if (!(obj instanceof ComponentStatus)) {
			return false;
		}
		return this.status.equals(((ComponentStatus) obj).status);
	}
	
	@Override
	public String toDisplayString() {

		String enum_name = status.toString();
		enum_name = enum_name.substring(0,1) + enum_name.substring(1).toLowerCase();
		
		return enum_name;
	}

}