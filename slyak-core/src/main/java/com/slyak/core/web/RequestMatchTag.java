/**
 * Project name : slyak-core
 * File name : RequestMatchTag.java
 * Package name : com.slyak.core.web
 * Date : 2014年1月14日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.slyak.core.web.util.WebUtils;

public class RequestMatchTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pattern;
	
	private String arguments;

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	@Override
	public int doStartTag() throws JspException {
		String[] patterns = StringUtils.split(pattern, ",");
		if(arguments!=null&&!WebUtils.argumentsMatch((HttpServletRequest) pageContext.getRequest(),arguments)){
			return SKIP_BODY;
		}
		for (String pt : patterns) {
			if(WebUtils.requestMatch((HttpServletRequest) pageContext.getRequest(), pt)){
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}
}
