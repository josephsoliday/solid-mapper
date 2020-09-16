package com.solid.converter;

/**
 * Builder for creating objects of type {@link Converter}.
 * 
 * @author Joseph Soliday
 *
 */
public class ConverterBuilder {
	private String customConverter;
	private Class<?> converter;
	
	public ConverterBuilder customConverter(final String customConverter) {
		this.customConverter = customConverter;
		return this;
	}
	
	public ConverterBuilder converter(final Class<?> converter) {
		this.converter = converter;
		return this;
	}
	
	public Converter build() {
		try {
            if (customConverter != null && !customConverter.isEmpty()) {
                return new CustomConverter(customConverter);
            } else if (converter != null && !converter.equals(Converter.class)) {
                return (Converter) converter.newInstance();
            }
            return null;
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new UnableToBuildConverterRuntimeException("Unable to get converter for custom mapping: " + e.getMessage(), e);
        }
	}
	
}
