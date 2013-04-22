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
