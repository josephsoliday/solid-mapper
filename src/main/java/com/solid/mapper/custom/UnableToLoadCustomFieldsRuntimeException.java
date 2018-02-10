package com.solid.mapper.custom;

/**
 * Thrown when there is an error loading custom fields.
 * 
 * @author Joseph
 *
 */
public class UnableToLoadCustomFieldsRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnableToLoadCustomFieldsRuntimeException(String message, Exception cause) {
		super(message, cause);
	}

}
