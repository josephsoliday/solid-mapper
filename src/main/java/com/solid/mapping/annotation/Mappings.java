package com.solid.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining mappings.
 * 
 * @author Joseph Soliday
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD}) // Can use in declaration only.
public @interface Mappings {
	
	/**
	 * Gets a list of mappings.
	 * 
	 * @return the mappings.
	 */
	Mapping[] value();
}
