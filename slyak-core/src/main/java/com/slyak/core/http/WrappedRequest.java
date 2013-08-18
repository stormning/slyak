package com.slyak.core.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.CollectionUtils;

@SuppressWarnings("rawtypes")
public class WrappedRequest extends HttpServletRequestWrapper {
	
	private HttpServletRequest nativeRequest;

	private Map<String,String> parameterMap;

	public WrappedRequest(HttpServletRequest request) {
		super(request);
		this.nativeRequest = request;
		this.parameterMap = new HashMap<String,String>(nativeRequest.getParameterMap());
	}

	public HttpServletRequest getNativeRequest() {
		return nativeRequest;
	}

	@Override
	public Map getParameterMap() {
		return this.parameterMap;
	}

	@Override
	public String getParameter(String name) {
		return parameterMap.get(name);
	}

	@Override
	public Enumeration getParameterNames() {
		final Set<String> paramNames = parameterMap.keySet();
		if(CollectionUtils.isEmpty(paramNames)){
			return null;
		}else{
			return new Enumeration<String>() {
				Iterator<String> pit = paramNames.iterator();
				@Override
				public boolean hasMoreElements() {
					return pit.hasNext();
				}
	
				@Override
				public String nextElement() {
					return pit.next();
				}
			};
		}
	}
	
	
	
	
	
	
	
}
