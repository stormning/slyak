package com.slyak.core.io;

public interface StaticResourceMappingManager {

	String getUploadPath();
	
	String getHttpPathPrefix();
	
	//use when upload
	String getRealPathByBizAndOwner(String biz,String owner);
	
	//use when view
	String getHttpPathByBizAndOwner(String biz,String owner);
}
