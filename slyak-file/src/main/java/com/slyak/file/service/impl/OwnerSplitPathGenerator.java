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

import com.slyak.file.service.OwnerPathGenerator;

public class OwnerSplitPathGenerator implements OwnerPathGenerator{

	@Override
	public String generateOwnerPath(String owner) {
		StringBuffer path = new StringBuffer();
		int begin = 0;
		int total = owner.length();
		int step = Math.min(2, owner.length()-begin);
		while(begin<total) {
			path.append(File.pathSeparatorChar).append(owner.substring(begin,step));
			begin += step;
			step = Math.min(2, owner.length()-begin);
		}
		return path.toString();
	}
	
	public static void main(String[] args) {
		String owner = "123";
		StringBuffer path = new StringBuffer();
		int begin = 0;
		int total = owner.length();
		int step = Math.min(2, owner.length()-begin);
		while(begin<total) {
			path.append(File.pathSeparatorChar).append(owner.substring(begin,step));
			begin += step;
			step = Math.min(2, owner.length()-begin);
		}
		System.out.println(path.toString());
	}

}
