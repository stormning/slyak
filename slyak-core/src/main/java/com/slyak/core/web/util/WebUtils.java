/**
 * Project name : slyak-core
 * File name : WebUtils.java
 * Package name : com.slyak.core.web.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;

public class WebUtils extends org.springframework.web.util.WebUtils {

    private static final String[] MOBILE_SPECIFIC_SUBSTRING = { "ipad", "iphone", "android", "midp", "opera mobi",
            "opera mini", "blackberry", "hp ipaq", "iemobile", "msiemobile", "windows phone", "htc", "lg", "mot",
            "nokia", "symbian", "fennec", "maemo", "tear", "midori", "armv", "windows ce", "windowsce", "smartphone",
            "240x320", "176x220", "320x320", "160x160", "webos", "palm", "sagem", "samsung", "sgh", "siemens",
            "sonyericsson", "mmp", "ucweb" };

    public static boolean isWapRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
            if (userAgent.contains(mobile)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equals(header);
    }
    
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void writeJson(Object result,Writer writer) throws IOException{
		writer.write(JSON.toJSONString(result));
	}
	
	public static void writeToResponse(String result,HttpServletResponse response) throws IOException{
		response.getWriter().write(result);
	}
	
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
	private static final UrlPathHelper PATH_HELPER = new UrlPathHelper();
	public static boolean requestMatch(HttpServletRequest request,String pattern){
		return PATH_MATCHER.match(pattern, PATH_HELPER.getOriginatingRequestUri(request));
	}
	
	public static void main(String[] args) {
		PathMatcher matcher = new AntPathMatcher();
		System.out.println(matcher.match("/user/**", "/user/1"));
	}

	public static boolean argumentsMatch(HttpServletRequest request,
			String arguments) {
		String[] argValueStrs = StringUtils.split(arguments,"&");
		for (String argValue : argValueStrs) {
			String[] ava =  StringUtils.split(argValue,"=");
			if(!StringUtils.equals(request.getParameter(ava[0]), ava[1])){
				return false;
			}
		}
		return true;
	}
}
