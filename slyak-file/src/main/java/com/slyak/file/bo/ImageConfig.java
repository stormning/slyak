/**
 * Project name : slyak-file
 * File name : ImageConfig.java
 * Package name : com.slyak.file.bo
 * Date : 2014年3月6日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.bo;

import com.slyak.core.io.image.ImgSize;

public class ImageConfig {
	private ImgSize imgSize;
	private String defaultImgLocation;
	public ImgSize getImgSize() {
		return imgSize;
	}
	public void setImgSize(ImgSize imgSize) {
		this.imgSize = imgSize;
	}
	public String getDefaultImgLocation() {
		return defaultImgLocation;
	}
	public void setDefaultImgLocation(String defaultImgLocation) {
		this.defaultImgLocation = defaultImgLocation;
	}
	
	
}
