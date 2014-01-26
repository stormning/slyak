/**
 * Project name : slyak-attachment
 * File name : AttachmentDao.java
 * Package name : com.slyak.attachment.dao
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.file.bo.VirtualFile;

public interface VirtualFileDao extends JpaRepository<VirtualFile, Long> {

	/**
	 * @param biz
	 * @param owner
	 * @return
	 */
	List<VirtualFile> findByBizAndOwner(String biz, String owner);

}
