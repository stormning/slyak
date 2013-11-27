/**
 * Project name : slyak-user
 * File name : LoginoutController.java
 * Package name : com.slyak.user.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slyak.user.util.LoginUserHelper;

@Controller
public class LoginoutController {
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(String username,String password,boolean rememberMe){
		try{
			LoginUserHelper.login(username, password, rememberMe);
		}  catch (AuthenticationException ae){
			//do noting
		} 
		return "redirect:/";
	}
	
	@RequestMapping(value="/doAjaxLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> doAjaxLogin(String username,String password,boolean rememberMe){
		Map<String,String> result = new HashMap<String, String>();
		try{
			LoginUserHelper.login(username, password, rememberMe);
			result.put("success", "1");
		}  catch (AuthenticationException ae){
			result.put("success", "0");
		} 
		return result;
	}

	@RequestMapping("/logout")
	public String logout(){
		LoginUserHelper.logout();
		return "redirect:/";
	}
}
