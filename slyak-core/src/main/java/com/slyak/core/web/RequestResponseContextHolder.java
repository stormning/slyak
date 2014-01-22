/**
 * Project name : slyak-core
 * File name : RequestResponseContextHolder.java
 * Package name : com.slyak.core.web
 * Date : 2014年1月17日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestResponseContextHolder {
	
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getHttpServletRequest(){
		return requestLocal.get();
	}
	
	public static HttpServletResponse getHttpServletResponse(){
		return responseLocal.get();
	}
	
	public static void init(HttpServletRequest request,HttpServletResponse response){
		requestLocal.set(request);
		responseLocal.set(response);
	}

	public static void clear() {
		requestLocal.remove();
		responseLocal.remove();
	}
}
