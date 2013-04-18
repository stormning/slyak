/*
 * Project:  any
 * Module:   slyak-web
 * File:     ImageController.java
 * Modifier: zhouning
 * Modified: 2012-12-9 下午7:18:01 
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

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slyak.core.io.image.CommonImage;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-9
 */
@Controller
@RequestMapping("/img")
public class ImageController {
	
	@RequestMapping("/{resourceType}/{resourceId}")
	public void img(@PathVariable long resourceType,@PathVariable long resourceId,HttpServletResponse response) throws IOException{
		new CommonImage(findLocationByResourceTypeAndResourceId(resourceType,resourceId)).send(response.getOutputStream());
	}

	private String findLocationByResourceTypeAndResourceId(
			long resourceType, long resourceId) {
		return "";
	}
}
