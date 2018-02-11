package com.solid.mapper.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.solid.converter.Converter;
import com.solid.mapper.mapping.MappingType;

/**
 * Annotation for defining a mapping.
 * 
 * @author Joseph Soliday
 * 
 */
@Repeatable(Mappings.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD}) // Can use in declaration only.
public @interface Mapping {

	/**
	 * Gets source property to map.
	 * 
	 * @return the source property map
	 */
	String source();
	
	/**
	 * Gets the converter to use for mapping the source.
	 * 
	 * @return the source converter
	 */
	Class<?> sourceConverter() default Converter.class;
	
	/**
	 * Gets the custom converter to use for mapping the source.
	 * 
	 * @return the custom source converter
	 */
	String customSourceConverter() default "";
	
	/**
	 * Gets the destination property to map.
	 * 
	 * @return the destination property to map
	 */
	String destination();
	
	/**
	 * Gets the converter to use for mapping the destination.
	 * 
	 * @return the destination converter
	 */
	Class<?> destinationConverter() default Converter.class;
	
	/**
	 * Gets the custom converter to use for mapping the destination.
	 * 
	 * @return the custom destination converter
	 */
	String customDestinationConverter() default "";
	
	/**
	 * Gets the type of mapping.
	 * 
	 * @return the type of mapping
	 */
	MappingType type();
	
}
