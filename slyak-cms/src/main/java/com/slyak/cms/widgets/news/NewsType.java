/**
 * Project name : slyak-cms
 * File name : NewsType.java
 * Package name : com.slyak.cms.widgets.news
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.widgets.news;

import com.slyak.core.io.image.ImgConfig;
import com.slyak.group.model.Group;

public class NewsType {

	private Group group;
	
	private ImgConfig imgConfig;

	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public ImgConfig getImgConfig() {
		return imgConfig;
	}

	public void setImgConfig(ImgConfig imgConfig) {
		this.imgConfig = imgConfig;
	}
}
