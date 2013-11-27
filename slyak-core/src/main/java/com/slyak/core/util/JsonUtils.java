/**
 * Project name : slyak-core
 * File name : JsonUtils.java
 * Package name : com.slyak.core.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.util;


import com.alibaba.fastjson.JSON;

public class JsonUtils {
	
	public static final String toJSON(Object object){
		return JSON.toJSONString(object);
	}
	
	public static final <T> T toType(String jsonString,Class<T> clazz){
		return JSON.parseObject(jsonString, clazz);
	}

}
