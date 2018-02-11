package com.solid.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for various reflection operations.
 */
public class ReflectionUtils {

	private static final String GETTER_PREFIX = "get";
	private static final String IS_PREFIX = "is";
	private static final String SETTER_PREFIX = "set";
    
    /**
     * Gets a list of fields from the object.
     * 
     * @param clazz the class
     * @return a map of fields
     */
    public static Map<String, Field> getFields(final Class<?> clazz) {
    	final Map<String, Field> fields = new HashMap<String, Field>();
    	final Field[] findFields = clazz.getDeclaredFields();
    	// TODO: Add deep copy to get object.getClass().getSuperClass().getDeclaredFields();
    	for (Field field: findFields) {
    		fields.put(field.getName(), field);
    	}
    	return fields;
    }

	/**
	 * Gets a list of getters from the object.
	 *
	 * @param clazz the class
	 * @return a map of getters
	 */
    public static Map<String, Method> getGetters(final Class<?> clazz) {
        // Get a list of getters for the object
        final Map<String, Method> getters = new HashMap<String, Method>();
        for (final Method method : clazz.getMethods()) {
            // See if a getter
            if (isGetter(method)) {
                getters.put(method.getName().replaceFirst(GETTER_PREFIX, "").replaceFirst(IS_PREFIX, ""), method);
            }
        }
        return getters;
    }

	/**
	 * Gets a list of setters from the object.
	 *
	 * @param clazz the class
	 * @return a map of setters
	 */
    public static Map<String, Method> getSetters(final Class<?> clazz) {
        // Get a list of setters for the object
        final Map<String, Method> setters = new HashMap<String, Method>();
        for (final Method method : clazz.getMethods()) {
            // See if a setter
            if (isSetter(method)) {
                setters.put(method.getName().replaceFirst(SETTER_PREFIX, ""), method);
            }
        }
        return setters;
    }

	/**
	 * Checks if the method is a getter.
	 *
	 * @param method the method
	 * @return whether or not the method is a getter.
	 */
    public static boolean isGetter(final Method method) {
        if (!method.getName().startsWith(GETTER_PREFIX) && !method.getName().startsWith(IS_PREFIX)) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        return true;
    }

	/**
	 * Checks if the method is a setter.
	 *
	 * @param method the method
	 * @return whether or not the method is a setter.
	 */
    public static boolean isSetter(final Method method) {
        if (!method.getName().startsWith(SETTER_PREFIX)) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }
}
