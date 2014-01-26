/**
 * Project name : slyak-attachment
 * File name : AttachmentServiceImpl.java
 * Package name : com.slyak.attachment.service.impl
 * Date : 2014年1月22日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

import com.slyak.file.bo.VirtualFile;
import com.slyak.file.dao.VirtualFileDao;
import com.slyak.file.service.FileService;
import com.slyak.file.service.OwnerPathGenerator;

@Service
public class FileServiceImpl implements FileService, ServletContextAware,
		InitializingBean {

	@Autowired
	private VirtualFileDao virtualFileDao;

	private String uploadPath = "file:///opt/upload";

	private ServletContext servletContext;

	private Map<String, OwnerPathGenerator> defaultPathGenerators = new HashMap<String, OwnerPathGenerator>();

	private Map<String, OwnerPathGenerator> ownerPathGenerators = new HashMap<String, OwnerPathGenerator>();

	private OwnerPathGenerator defaultOwnerPathGenerator = new OwnerSplitPathGenerator();

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	{
		defaultPathGenerators
				.put(BIZ_TEXT_EDITOR, new OwnerDatePathGenerator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slyak.file.service.FileService#findOne(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public com.slyak.file.bo.VirtualFile findVirtual(String biz, String owner) {
		List<VirtualFile> virtualFiles = virtualFileDao.findByBizAndOwner(biz,
				owner);
		if (!CollectionUtils.isEmpty(virtualFiles)) {
			return virtualFiles.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slyak.file.service.FileService#getOriginalFile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public File findReal(String biz, String owner, String fileName) {
		StringBuffer path = new StringBuffer(uploadPath);
		path.append(getRelativePath(biz, owner, fileName));
		return new File(StringUtils.cleanPath(path.toString()));
	}

	private String getRelativePath(String biz, String owner, String fileName) {
		StringBuffer path = new StringBuffer();
		if (StringUtils.hasText(biz)) {
			path.append(File.pathSeparatorChar).append(biz);
		}
		if (StringUtils.hasText(owner)) {
			path.append(File.pathSeparatorChar)
					.append(ownerToPath(null, owner));
		}
		if (StringUtils.hasText(fileName)) {
			path.append(File.pathSeparatorChar).append(fileName);
		}
		return StringUtils.cleanPath(path.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slyak.file.service.FileService#ownerToPath(java.lang.String)
	 */
	@Override
	public String ownerToPath(String biz, String owner) {
		OwnerPathGenerator ownerPathGenerator = ownerPathGenerators.get(biz);
		if (ownerPathGenerator == null) {
			ownerPathGenerator = defaultOwnerPathGenerator ;
		}
		return ownerPathGenerator.generateOwnerPath(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slyak.file.service.FileService#createVirtual(com.slyak.file.bo.
	 * VirtualFile)
	 */
	@Override
	public void saveVirtual(VirtualFile virtualFile) {
		virtualFileDao.save(virtualFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slyak.file.service.FileService#getFileHttpPath(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getFileHttpPath(String requestPrefix, String biz,
			String owner, String fileName) {
		return servletContext.getContextPath() + "/" + requestPrefix
				+ getRelativePath(biz, owner, fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ServletContextAware#setServletContext
	 * (javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (ownerPathGenerators == null) {
			ownerPathGenerators = defaultPathGenerators;
		} else {
			ownerPathGenerators.putAll(defaultPathGenerators);
		}
	}
}
