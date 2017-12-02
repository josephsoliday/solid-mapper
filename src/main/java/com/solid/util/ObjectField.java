package com.solid.util;

import java.lang.reflect.Field;

/**
 * Class representing an object and corresponding field.
 */
public class ObjectField {
	private Object object;
	private Field field;
	private String fieldName;
	
	public ObjectField(final Object object, final Field field, final String fieldName) {
		this.object = object;
		this.field = field;
		this.fieldName = fieldName;
	}
	
	public Object getObject() {
		return object;
	}
	
	public Field getField() {
		return field;
	}
	
	public String getFieldName() {
		return fieldName;
	}
}