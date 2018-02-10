package com.solid.converter;

/**
 * Interface for converting an object one type to an object of another type.
 * 
 * @author Joseph Soliday
 *
 */
public interface Converter {
	
	/**
	 * Converts an object of type {@link S} to an object of type {@link D}.
	 * 
	 * @param the object to convert
	 * @param type the type of the object to convert
	 * @return the converted object
	 * @throws UnableToConvertRuntimeException thrown when a conversion exception occurs
	 */
	public <S,D> D convert(S object, Class<?> type) throws UnableToConvertRuntimeException;
}
