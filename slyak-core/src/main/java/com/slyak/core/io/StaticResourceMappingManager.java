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
