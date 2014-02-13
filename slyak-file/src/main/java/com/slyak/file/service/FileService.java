/**
 * Project name : slyak-attachment
 * File name : AttachmentService.java
 * Package name : com.slyak.attachment.service
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service;

import java.io.File;

import com.slyak.file.bo.VirtualFile;

public interface FileService {
	
	String BIZ_TEXT_EDITOR = "textEditor";
	
	String ORIGINAL_FILE = "original";
	
	String TMP_FILE = "tmp";
	
	void saveVirtual(VirtualFile virtualFile);

	VirtualFile findVirtual(String biz, String owner);

	/**
	 * return file or directory
	 * @param biz
	 * @param owner
	 * @param fileName
	 * @return
	 */
	File findReal(String biz, String owner ,String fileName);
	
	String getFileHttpPath(String requestPrefix,String biz, String owner ,String fileName);
	
	String ownerToPath(String biz, String owner);
}
