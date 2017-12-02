package com.solid.mapper;

public class CustomMappingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CustomMappingException(String message, Exception cause) {
		super(message, cause);
	}

}
