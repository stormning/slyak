/**
 * Project name : slyak-core
 * File name : WrappedRequest.java
 * Package name : com.slyak.core.http
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.http;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class WrappedRequest extends HttpServletRequestWrapper {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public WrappedRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Object getAttribute(String name) {
		Object result = super.getAttribute(name);
		if (result == null) {
			return attributes.get(name);
		} else {
			return result;
		}
	}

	@Override
	public void setAttribute(String name, Object o) {
		attributes.put(name, o);
	}
}
