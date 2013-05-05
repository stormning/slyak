package com.slyak.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.slyak.user.util.LoginUserHelper;

@Controller
public class LoginoutController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,boolean rememberMe){
		LoginUserHelper.login(username, password, rememberMe);
		return "redirect:/";
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "alone:core.login";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		LoginUserHelper.logout();
		return "redirect:/";
	}
}
