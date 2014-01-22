/**
 * Project name : slyak-core
 * File name : RequestResponseContextHolderFilter.java
 * Package name : com.slyak.core.web
 * Date : 2014年1月17日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class RequestResponseContextHolderFilter extends OncePerRequestFilter{
	
	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		RequestResponseContextHolder.init(request, response);
		try{
			filterChain.doFilter(request, response);
		} finally {
			RequestResponseContextHolder.clear();
		}
	}


}
