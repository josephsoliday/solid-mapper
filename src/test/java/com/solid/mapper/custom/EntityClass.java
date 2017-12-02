package com.solid.mapper.custom;

public class EntityClass extends BaseEntity {
	private String string1;
	private Integer integer1;
	private boolean boolean1;
	private Integer size;
	private EntityClassSubType element = new EntityClassSubType(null, false);
	@SuppressWarnings("unused")
	private String privateValue = "EntityClass";
	private int convertValue;

	public EntityClass() {
		
	}
	
	public EntityClass(String string1, Integer integer1, boolean boolean1) {
		this.string1 = string1;
		this.integer1 = integer1;
		this.boolean1 = boolean1;
	}
	
	public EntityClass(String string1, Integer integer1, boolean boolean1, Integer size, EntityClassSubType element, int convertValue) {
		this.string1 = string1;
		this.integer1 = integer1;
		this.boolean1 = boolean1;
		this.size = size;
		this.element = element;
		this.convertValue = convertValue;
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
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public EntityClassSubType getElement() {
		return element;
	}

	public void setElement(EntityClassSubType element) {
		this.element = element;
	}
	
	public int getConvertValue() {
		return convertValue;
	}

	public void setConvertValue(int convertValue) {
		this.convertValue = convertValue;
	}
}
