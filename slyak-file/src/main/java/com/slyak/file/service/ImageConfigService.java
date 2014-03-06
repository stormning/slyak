/**
 * Project name : slyak-file
 * File name : ImageConfigService.java
 * Package name : com.slyak.file.service
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service;

import java.io.File;
import java.util.Map;

import com.slyak.file.bo.ImageConfig;

public interface ImageConfigService {

	ImageConfig findImageConfig(String biz, String fileName);

	Map<String, ImageConfig> findImageConfigs(String biz);
	
	File findDefaultImage(String biz,String fileName);
	
}
