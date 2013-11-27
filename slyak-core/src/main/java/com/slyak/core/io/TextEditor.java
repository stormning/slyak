/**
 * Project name : slyak-core
 * File name : TextEditor.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.util.Map;

public interface TextEditor {

	Map<String,Object> onFileUploadSuccess(String webPath);
	
	Map<String,Object> onFileUploadFailed(Exception e);
	
	Map<String,Object> listFiles(String rootPath,String dir,String order);
}
