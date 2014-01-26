/**
 * Project name : slyak-file
 * File name : SimpleImageConfigSerivce.java
 * Package name : com.slyak.file.service.impl
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service.impl;

import java.util.Map;

import com.slyak.core.io.image.ImgSize;
import com.slyak.file.service.ImageConfigService;

public class SimpleImageConfigSerivce implements ImageConfigService{
	
	private Map<String,Map<String, ImgSize>> bizTypeImgSizeConfig;
	
	/* (non-Javadoc)
	 * @see com.slyak.file.service.ImageConfigService#findSize(java.lang.String, java.lang.String)
	 */
	public void setBizTypeImgSizeConfig(
			Map<String, Map<String, ImgSize>> bizTypeImgSizeConfig) {
		this.bizTypeImgSizeConfig = bizTypeImgSizeConfig;
	}

	@Override
	public ImgSize findSize(String biz, String type) {
		//biz:{small:40x40,big:60x60}
		Map<String, ImgSize> typeImgSizes = findSizes(biz);
		if(typeImgSizes!=null) {
			return typeImgSizes.get(type);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.slyak.file.service.ImageConfigService#findSizes(java.lang.String)
	 */
	@Override
	public Map<String, ImgSize> findSizes(String biz) {
		return bizTypeImgSizeConfig.get(biz);
	}

}
