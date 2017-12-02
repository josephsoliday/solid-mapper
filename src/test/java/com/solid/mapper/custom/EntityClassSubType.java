package com.solid.mapper.custom;

public class EntityClassSubType {
	private String value;
	private boolean checked;
	
	public EntityClassSubType(final String value, final boolean checked) {
		this.value = value;
		this.checked = checked;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
