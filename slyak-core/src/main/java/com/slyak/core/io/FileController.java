/**
 * Project name : slyak-core
 * File name : FileController.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController implements ApplicationContextAware{
	
	@Autowired
	private StaticResourceMappingManager fileUplodaeManager;
	
	private ApplicationContext applicationContext;
	
	private TextEditor textEditor = new Kindeditor();
	
	
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String upload(){
		return "file.upload";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String upload(MultipartFile file,String owner,String dir) throws IOException{
		uploadAndGetUrl(file,owner);
		return "file.upload";
	}
	
	@RequestMapping(value="/textEditor/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> textEditorUpload(MultipartFile file){
		String url;
		try {
			url = uploadAndGetUrl(file,"textEditor");
			return textEditor.onFileUploadSuccess(url);
		} catch (Exception e) {
			return textEditor.onFileUploadFailed(e);
		}
	}
	
	@RequestMapping(value="/textEditor",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> textEditorFiles(String order,String path){
		try{
			Resource resource = resourceLoader.getResource(StringUtils.cleanPath(fileUplodaeManager.getUploadPath()+File.separator+"textEditor"));
			return textEditor.listFiles(resource.getFile().getAbsolutePath(),path,order);
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}
	
	private String uploadAndGetUrl(MultipartFile file,String owner) throws IOException{
		CommonFile cf = new CommonFile(file.getInputStream());
		String relativePath = generatePath(file,owner);
		Resource resource = resourceLoader.getResource(StringUtils.cleanPath(fileUplodaeManager.getUploadPath()+File.separator+relativePath));
		cf.save(resource.getFile());
		//url for web
		return "/file"+relativePath.replace('\\', '/');
	}
	
	private String generatePath(MultipartFile file,String owner){
		if(owner == null){
			return generateDataFolder()+File.separator+file.getOriginalFilename();
		}else{
			return StringUtils.cleanPath(generatePathByOwner(file,owner));
		}
	}
	
	private String generateDataFolder() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return File.separator+df.format(new Date());
	}

	private String generatePathByOwner(MultipartFile file,String owner){
		Map<String,FilePathGenerator> filePathGenerators = applicationContext.getBeansOfType(FilePathGenerator.class);
		if(!CollectionUtils.isEmpty(filePathGenerators)){
			Iterator<FilePathGenerator> pathIterator = filePathGenerators.values().iterator();
			while (pathIterator.hasNext()) {
				FilePathGenerator fpg = pathIterator.next();
				if(fpg.supports(owner)){
					return fpg.generatePath(file,owner);
				}
			}
		}
		return File.separator+owner+generateDataFolder()+File.separator+file.getOriginalFilename();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static void main(String[] args) {
//		System.out.println(StringUtils.replaceEach("/WEB-INF\\a", new String[]{"/","\\"}, new String[]{File.separator,File.separator}));
//		FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
//		Path
//		ResourceLoader resourceLoader = new DefaultResourceLoader();
//		Resource resource = resourceLoader.getResource("file:///opt/aaa/bbb");
//		System.out.println(resource.exists());
	}
}
