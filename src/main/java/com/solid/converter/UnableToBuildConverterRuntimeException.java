package com.solid.converter;

/**
 * Thrown when a an object of type {@link Converter} could not be built.
 * 
 * @author Joseph
 *
 */
public class UnableToBuildConverterRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnableToBuildConverterRuntimeException(String message, Exception cause) {
		super(message, cause);
	}

}
