package com.solid.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Custom class for converting objects between types.
 * 
 * @author Joseph Soliday
 *
 */
public class CustomConverter implements Converter {

	private Class<?> clazz;
	private String methodName;
	private Method method;
	
	public CustomConverter(final String converterString) {
		String[] values = converterString.split("\\.");
		methodName = values[values.length - 1];
		final String className = converterString.replace("." + methodName, "");
		try {
			clazz = Class.forName(className);
		} catch (final ClassNotFoundException e) {
			throw new UnableToBuildConverterRuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S,D> D convert(S object, Class<?> type) throws UnableToConvertRuntimeException {
		try {
			return (D) getMethod(type).invoke(null, object);
		} catch (final NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new UnableToConvertRuntimeException("Unable to convert.", e);
		}
	}
	
	private <S> Method getMethod(Class<?> type) throws NoSuchMethodException, SecurityException {
		if (method == null) {
			method = clazz.getDeclaredMethod(methodName, type);
		}
		return method;
	}
}
