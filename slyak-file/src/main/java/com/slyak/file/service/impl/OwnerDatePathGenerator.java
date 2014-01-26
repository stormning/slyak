/**
 * Project name : slyak-file
 * File name : OwnerDatePathGenerator.java
 * Package name : com.slyak.file.service.impl
 * Date : 2014年1月23日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.slyak.file.service.OwnerPathGenerator;

public class OwnerDatePathGenerator implements OwnerPathGenerator {

	@Override
	public String generateOwnerPath(String owner) {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}

}
