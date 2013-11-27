/**
 * Project name : slyak-core
 * File name : FilePathGenerator.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import org.springframework.web.multipart.MultipartFile;

public interface FilePathGenerator {
	
	boolean supports(String owner);
	
	String generatePath(MultipartFile file,String owner);
}
