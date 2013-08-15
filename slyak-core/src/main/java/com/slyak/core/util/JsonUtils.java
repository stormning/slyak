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
