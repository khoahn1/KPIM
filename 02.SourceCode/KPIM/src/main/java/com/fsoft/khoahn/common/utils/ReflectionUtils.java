package com.fsoft.khoahn.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

	/**
	 * toBeanType
	 * 
	 * @param type
	 * @return result
	 */
	public static Class<?> toBeanType(final Type type) {
		Class<?> result = null;
		if (type instanceof ParameterizedType) {
			result = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
		return result;
	}

}
