package com.slyak.core.util;


import com.alibaba.fastjson.JSON;

public class JsonUtils {
	
	public static final String toJSON(Object object){/*
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StringUtils.EMPTY;
	*/
		return JSON.toJSONString(object);
	}
	
	public static final <T> T toType(String jsonString,Class<T> clazz){/*
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(object, clazz);
	*/
//		JSON.toJavaObject(n, clazz);
		return JSON.parseObject(jsonString, clazz);
	}

}
