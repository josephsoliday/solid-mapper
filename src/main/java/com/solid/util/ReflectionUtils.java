package com.solid.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
     * @param object the object
     * @return a map of fields
     */
    public static Map<String, Field> getFields(final Object object) {
    	final Map<String, Field> fields = new HashMap<String, Field>();
    	final Field[] findFields = object.getClass().getDeclaredFields();
    	// TODO: Add deep copy to get object.getClass().getSuperClass().getDeclaredFields();
    	for (Field field: findFields) {
    		fields.put(field.getName(), field);
    	}
    	return fields;
    }

	/**
	 * Gets a list of getters from the object.
	 *
	 * @param object the object
	 * @return a map of getters
	 */
    public static Map<String, Method> getGetters(final Object object) {
        // Get a list of getters for the object
        final Map<String, Method> getters = new HashMap<String, Method>();
        for (final Method method : object.getClass().getMethods()) {
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
	 * @param object the object
	 * @return a map of setters
	 */
    public static Map<String, Method> getSetters(final Object object) {
        // Get a list of setters for the object
        final Map<String, Method> setters = new HashMap<String, Method>();
        for (final Method method : object.getClass().getMethods()) {
            // See if a setter
            if (isSetter(method)) {
                setters.put(method.getName().replaceFirst(SETTER_PREFIX, ""), method);
            }
        }
        return setters;
    }
    
    public static ObjectMethod getObjectMethod(final Object object, final String name, final Class<?> type, boolean getter) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Method method = null;
    	Object parentObject = object;
    	
    	if (name.contains(".")) {
    		final String[] names = name.split("\\.");
    		for (int i = 0; i < names.length; i++) {
    			if (i < names.length - 1) {
    				method = getMethod(parentObject, names[i], null, true);
    				parentObject = method.invoke(object, new Object[] {});
    			} else {
					method = getMethod(parentObject, names[i], type, getter);
    			}
    		}
    	} else {
    		method = getMethod(parentObject, name, type, getter);
    	}
    	return new ObjectMethod(parentObject, type, method, name);
    }
    
    public static ObjectField getObjectField(final Object object, final String name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	Field field = null;
    	Object parentObject = object;
    	
    	if (name.contains(".")) {
    		final String[] names = name.split("\\.");
    		for (int i = 0; i < names.length; i++) {
    			if (i < names.length - 1) {
    				field = parentObject.getClass().getDeclaredField(names[i]);
    				field.setAccessible(true);
    				parentObject = field.get(parentObject);
    			} else {
					field = parentObject.getClass().getDeclaredField(names[i]);
    			}
    		}
    	} else {
    		field = parentObject.getClass().getDeclaredField(name);
    	}
    	return new ObjectField(parentObject, field, name);
    }
    
	private static Method getMethod(final Object object, final String name, final Class<?> type, boolean getter) throws NoSuchMethodException, SecurityException {
		return getter ? object.getClass().getDeclaredMethod(type == boolean.class ? IS_PREFIX + capitalize(name): GETTER_PREFIX + capitalize(name))
				: object.getClass().getDeclaredMethod(SETTER_PREFIX + capitalize(name), type);
	}
	
	private static String capitalize(final String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
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
