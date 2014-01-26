/**
 * Project name : slyak-file
 * File name : ImageConfigService.java
 * Package name : com.slyak.file.service
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service;

import java.util.Map;

import com.slyak.core.io.image.ImgSize;

public interface ImageConfigService {

	ImgSize findSize(String biz, String fileName);

	Map<String, ImgSize> findSizes(String biz);

}
