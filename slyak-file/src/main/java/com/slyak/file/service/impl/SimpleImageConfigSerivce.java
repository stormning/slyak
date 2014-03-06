/**
 * Project name : slyak-file
 * File name : SimpleImageConfigSerivce.java
 * Package name : com.slyak.file.service.impl
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import com.slyak.file.bo.ImageConfig;
import com.slyak.file.service.ImageConfigService;

public class SimpleImageConfigSerivce implements ImageConfigService{
	
	
	private Map<String,Map<String, ImageConfig>> bizTypeImgSizeConfig;
	
	/* (non-Javadoc)
	 * @see com.slyak.file.service.ImageConfigService#findSize(java.lang.String, java.lang.String)
	 */
	public void setBizTypeImgSizeConfig(
			Map<String, Map<String, ImageConfig>> bizTypeImgSizeConfig) {
		this.bizTypeImgSizeConfig = bizTypeImgSizeConfig;
	}

	@Override
	public ImageConfig findImageConfig(String biz, String type) {
		//biz:{small:40x40,big:60x60}
		Map<String, ImageConfig> typeImgSizes = findImageConfigs(biz);
		if(typeImgSizes!=null) {
			return typeImgSizes.get(type);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.slyak.file.service.ImageConfigService#findSizes(java.lang.String)
	 */
	@Override
	public Map<String, ImageConfig> findImageConfigs(String biz) {
		return bizTypeImgSizeConfig.get(biz);
	}

	/* (non-Javadoc)
	 * @see com.slyak.file.service.ImageConfigService#getDefaultImage(java.lang.String, java.lang.String)
	 */
	@Override
	public File findDefaultImage(String biz, String fileName) {
		ImageConfig imageConfig = findImageConfig(biz, fileName);
		if (imageConfig == null) {
			return null;
		}
		try {
			return ResourceUtils.getFile(imageConfig.getDefaultImgLocation());
		} catch (FileNotFoundException e) {
			return null;
		}
	}

}
