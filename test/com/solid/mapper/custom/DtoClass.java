package com.solid.mapper.custom;

public class DtoClass extends BaseDto {
	private String string1;
	private Integer integer1;
	private boolean boolean1;
	private Integer length;
	private String value;
	private boolean checked;
	@SuppressWarnings("unused")
	private String privateValue = "DtoClass";
	private String convertValue;

	public DtoClass() {
		
	}
	
	public DtoClass(String string1, Integer integer1, boolean boolean1) {
		this.string1 = string1;
		this.integer1 = integer1;
		this.boolean1 = boolean1;
	}
	
	public DtoClass(String string1, Integer integer1, boolean boolean1, Integer length, String value, boolean checked) {
		this.string1 = string1;
		this.integer1 = integer1;
		this.boolean1 = boolean1;
		this.length = length;
		this.value = value;
		this.checked = checked;
	}

	public String getString1() {
		return string1;
	}
	public void setString1(String string1) {
		this.string1 = string1;
	}
	
	public Integer getInteger1() {
		return integer1;
	}
	public void setInteger1(Integer integer1) {
		this.integer1 = integer1;
	}
	
	public boolean isBoolean1() {
		return boolean1;
	}

	public void setBoolean1(boolean boolean1) {
		this.boolean1 = boolean1;
	}
	
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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
	
	public String getConvertValue() {
		return convertValue;
	}

	public void setConvertValue(String convertValue) {
		this.convertValue = convertValue;
	}
}
