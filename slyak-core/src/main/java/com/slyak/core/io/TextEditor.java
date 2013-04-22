package com.slyak.core.io;

import java.util.Map;

public interface TextEditor {

	Map<String,Object> onFileUploadSuccess(String webPath);
	
	Map<String,Object> onFileUploadFailed(Exception e);
	
	Map<String,Object> listFiles(String rootPath,String dir,String order);
}
