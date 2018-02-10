package com.solid.mapper.custom;

/**
 * Thrown when there is an error loading custom properties.
 * 
 * @author Joseph
 *
 */
public class UnableToLoadCustomPropertiesRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnableToLoadCustomPropertiesRuntimeException(String message, Exception cause) {
		super(message, cause);
	}

}
