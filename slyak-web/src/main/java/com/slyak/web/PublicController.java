/*
 * Project:  any
 * Module:   slyak-web
 * File:     LoginoutController.java
 * Modifier: zhouning
 * Modified: 2012-12-14 下午2:17:48 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.web;

import javax.validation.Valid;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slyak.user.model.User;
import com.slyak.user.service.UserService;
import com.slyak.user.util.LoginUserHelper;

/**
 * Those pages are public.
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-14
 */
@Controller
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/doRegist")
	@ResponseBody
	public int doRegist(@Valid User user,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return 3;
		}
		if(userService.exist(user.getEmail())){
			return 2;
		}else{
			userService.regist(user);
			return 0;
		}
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public int doLogin(User user,boolean rememberMe){
		try{
			LoginUserHelper.login(user.getEmail(), user.getPassword(), rememberMe);
			return 0;
		} catch (UnknownAccountException uae) {
			return 1;
		} catch (IncorrectCredentialsException ice){
			return 1;
		}
	}
	
	
	@RequestMapping("/logout")
	public String logout(User user,boolean rememberMe){
		LoginUserHelper.logout();
		return "redirect:/";
	}
	
}
