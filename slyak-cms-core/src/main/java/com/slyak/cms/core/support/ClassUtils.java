/**
 * Project name : slyak-cms-core
 * File name : ClassUtils.java
 * Package name : com.slyak.cms.core.support
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
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
