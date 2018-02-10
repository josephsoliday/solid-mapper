package com.solid.converter;

/**
 * Thrown when a conversion exception occurs.
 * 
 * @author Joseph Soliday
 *
 */
public class UnableToConvertRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnableToConvertRuntimeException(String message, Exception cause) {
		super(message, cause);
	}

}
