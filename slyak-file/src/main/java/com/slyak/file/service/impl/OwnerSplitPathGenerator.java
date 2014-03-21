/**
 * Project name : slyak-file
 * File name : OwnerSplitPathGenerator.java
 * Package name : com.slyak.file.service.impl
 * Date : 2014年1月23日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.slyak.file.service.OwnerPathGenerator;

public class OwnerSplitPathGenerator implements OwnerPathGenerator {

	@Override
	public String generateOwnerPath(String owner) {
		if(StringUtils.isBlank(owner)) {
			return StringUtils.EMPTY;
		}
		int len = owner.length();
		int begin = 0;
		int setp = 2;
		StringBuffer path = new StringBuffer();
		while(begin < len) {
			int end = Math.min(begin + setp, len);
			path.append(File.separatorChar).append(owner.substring(begin, end));
			begin = end;
		}
		return path.toString();
	}
}
