/**
 * Project name : slyak-web
 * File name : PublicController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;

import javax.validation.Valid;

import org.apache.shiro.authc.AuthenticationException;
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
	public int doRegist(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return 3;
		}
		if (userService.exist(user.getEmail())) {
			return 2;
		} else {
			userService.regist(user);
			return 0;
		}
	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public int doLogin(User user, boolean rememberMe) {
		try {
			LoginUserHelper.login(user.getEmail(), user.getPassword(),
					rememberMe);
			return 0;
		} catch (UnknownAccountException uae) {
			return 1;
		} catch (IncorrectCredentialsException ice) {
			return 1;
		} catch (AuthenticationException ae) {
			return 1;
		}
	}

	@RequestMapping("/logout")
	public String logout(User user, boolean rememberMe) {
		LoginUserHelper.logout();
		return "redirect:/";
	}

}
