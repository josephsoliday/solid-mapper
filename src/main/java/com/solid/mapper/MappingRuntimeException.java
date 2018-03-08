package com.solid.mapper;

/**
 * Thrown when an exception occurs mapping object fields.
 * 
 * @author Joseph Soliday
 *
 */
public class MappingRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public MappingRuntimeException(String message, Exception cause) {
		super(message, cause);
	}
}
