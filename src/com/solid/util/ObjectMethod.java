package com.solid.util;

import java.lang.reflect.Method;

/**
 * Class representing an object and corresponding method.
 */
public class ObjectMethod {
	private Object object;
	final Class<?> type;
	private Method method;
	private String methodName;
	
	public ObjectMethod(final Object object, final Class<?> type, final Method method, final String methodName) {
		this.object = object;
		this.type = type;
		this.method = method;
		this.methodName = methodName;
	}
	
	public Object getObject() {
		return object;
	}
	
	public final Class<?> getType() {
		return type;
	}

	public Method getMethod() {
		return method;
	}
	
	public String getMethodName() {
		return methodName;
	}
}