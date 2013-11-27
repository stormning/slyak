/**
 * Project name : slyak-web
 * File name : OtherController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/other")
public class OtherController {

	@ModelAttribute
	public void populateModel(Model model) {
       model.addAttribute("nav", "other");
    }
	
	
	@RequestMapping
	public String index(){
		return "other.index";
	}
	
}
