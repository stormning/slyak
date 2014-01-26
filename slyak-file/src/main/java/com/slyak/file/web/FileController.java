/**
 * Project name : slyak-core
 * File name : FileController.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.slyak.core.io.Kindeditor;
import com.slyak.core.io.TextEditor;
import com.slyak.core.io.image.CommonImage;
import com.slyak.core.io.image.ImgSize;
import com.slyak.file.bo.VirtualFile;
import com.slyak.file.service.FileService;
import com.slyak.file.service.ImageConfigService;

@Controller
@RequestMapping("/file")
public class FileController{
	
	private static final String REQUEST_PREFIX = "/file";
	
	private TextEditor textEditor = new Kindeditor();
	
	@Autowired(required = false)
	private ImageConfigService imageConfigService ;
	
	@Autowired(required = false)
	private FileService fileService;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public VirtualFile uploadOriginalFile(HttpServletRequest request,HttpServletResponse response,MultipartFile file,String biz,String owner) throws IOException{
		fileService.findReal(biz, owner, FileService.ORIGINAL_FILE);
		FileCopyUtils.copy(file.getInputStream(), response.getOutputStream());
		VirtualFile virtualFile = new VirtualFile();
		virtualFile.setBiz(biz);
		virtualFile.setOwner(owner);
		virtualFile.setContentType(file.getContentType());
		virtualFile.setName(file.getName());
		virtualFile.setSize(file.getSize());
		fileService.saveVirtual(virtualFile);
		return virtualFile;
	}
	
	@RequestMapping(value="/download/{biz}/{owner}",method=RequestMethod.POST)
	public ResponseEntity<byte[]> download(@PathVariable String biz,@PathVariable String owner) throws IOException{
		File original = fileService.findReal(biz,owner,FileService.ORIGINAL_FILE);
		
		if(original.exists()) {
			HttpHeaders headers = new HttpHeaders();  
		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		    VirtualFile virtualFile = fileService.findVirtual(biz,owner);
		    headers.setContentDispositionFormData("attachment", virtualFile.getName());
		    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(original),headers, HttpStatus.CREATED);
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/view/{biz}/{owner}/{fileName}")
	public void view(HttpServletResponse response,@PathVariable String biz,@PathVariable String owner,@PathVariable String fileName) throws IOException{
		//find size config
		ImgSize imgSize = imageConfigService.findSize(biz,fileName);
		if(imgSize!=null) {
			//find file in size
			File fileInType = fileService.findReal(biz, owner ,fileName);
			if(!fileInType.exists()) {
				File original = fileService.findReal(biz, owner,FileService.ORIGINAL_FILE);
				if(original.exists()) {
					//resize
					new CommonImage(original).resizeWithContainer(imgSize.getWidth(), imgSize.getHeight()).save(fileInType);
				}
			}
			if(fileInType.exists()) {
				FileCopyUtils.copy(new FileInputStream(fileInType), response.getOutputStream());
			}
		}
	}
	
	@RequestMapping(value="/textEditor/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> textEditorUpload(HttpServletRequest request,MultipartFile file){
		try {
			fileService.findReal(FileService.BIZ_TEXT_EDITOR, null, file.getOriginalFilename());
			return textEditor.onFileUploadSuccess(fileService.getFileHttpPath(REQUEST_PREFIX,FileService.BIZ_TEXT_EDITOR, null, file.getOriginalFilename()));
		} catch (Exception e) {
			return textEditor.onFileUploadFailed(e);
		}
	}
	
	@RequestMapping(value="/textEditor",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> textEditorFiles(String order,String path){
		try{
			File folder = fileService.findReal(FileService.BIZ_TEXT_EDITOR, null, null);
			return textEditor.listFiles(folder.getPath(),path,order);
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}
}
