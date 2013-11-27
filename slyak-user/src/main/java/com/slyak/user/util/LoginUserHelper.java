/**
 * Project name : slyak-user
 * File name : LoginUserHelper.java
 * Package name : com.slyak.user.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slyak.user.model.User;
import com.slyak.user.service.UserService;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-13
 */
@Component
public class LoginUserHelper {

	private static UserService userService;

	@Autowired
	public void init(UserService userService) {
		LoginUserHelper.userService = userService;
	}
	
	public static Long getLoginUserId(){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser!=null){
			return (Long) currentUser.getPrincipal();
		}
		return null;
	}
	
	public static User getLoginUser() {
		Long userId = getLoginUserId();
		return userId == null ? null : userService.getUser(userId);
	}

	public static void login(String username, String password,
			boolean rememberMe) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		token.setRememberMe(rememberMe);
		currentUser.login(token);
	}

	public static void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}

}
