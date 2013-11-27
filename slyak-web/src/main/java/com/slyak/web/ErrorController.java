/**
 * Project name : slyak-web
 * File name : ErrorController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	
	@RequestMapping("/default")
	public String errorDefault(){
		return "error.default";
	}
	
	@RequestMapping("/404")
	public String error404(){
		return "error.404";
	}
}
