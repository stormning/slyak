/**
 * Project name : slyak-core
 * File name : StaticResourceMappingManager.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import org.springframework.core.io.Resource;

public interface StaticResourceMappingManager {

	String getUploadPath();
	
	String getHttpPathPrefix();
	
	//use when upload
	Resource getRealPathByBizAndOwner(String biz,String owner);
	
	//use when view
	String getHttpPathByBizAndOwner(String biz,String owner);
}
