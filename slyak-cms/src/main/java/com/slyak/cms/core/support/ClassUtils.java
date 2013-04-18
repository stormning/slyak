package com.slyak.cms.core.support;

import java.lang.reflect.Method;

public class ClassUtils extends org.springframework.util.ClassUtils {
	public static Method getMethodByName(Class<?> clazz,String methodName){
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if(method.getName().equals(methodName)){
				return method;
			}
		}
		return null;
	}
}
