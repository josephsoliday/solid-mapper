package com.solid.util;

public class StringUtils {
	public static String capitalize(final String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
}
