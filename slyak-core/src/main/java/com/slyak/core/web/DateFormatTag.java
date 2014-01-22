/**
 * Project name : slyak-core
 * File name : LongToDate.java
 * Package name : com.slyak.core.web
 * Date : 2014年1月17日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.time.DateFormatUtils;

import com.slyak.core.util.DateUtils;

public class DateFormatTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Object date;
	private String pattern;

	private Date time;

	public void setDate(Object date) {
		this.date = date;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public int doStartTag() throws JspException {
		if (date != null) {
			String info;
			if (date instanceof Number) {
				time = new Date(((Number) date).longValue());
			} else if (date instanceof String) {
				time = new Date(Long.valueOf((String) date));
			} else {
				time = (Date) date;
			}

			if (pattern == null) {
				info = DateUtils.getDateInfo(time);
			} else {
				info = DateFormatUtils.format(time, pattern);
			}

			try {
				pageContext.getOut().print(info);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SKIP_BODY;
	}

}
