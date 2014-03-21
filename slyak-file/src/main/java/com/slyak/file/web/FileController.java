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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UrlPathHelper;

import com.slyak.core.io.Kindeditor;
import com.slyak.core.io.TextEditor;
import com.slyak.core.io.image.CommonImage;
import com.slyak.core.io.image.ImgSize;
import com.slyak.file.bo.ImageConfig;
import com.slyak.file.bo.VirtualFile;
import com.slyak.file.service.FileService;
import com.slyak.file.service.ImageConfigService;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/file")
public class FileController implements InitializingBean{
	
	private static final String REQUEST_PREFIX = "/file";
	
	private TextEditor textEditor = new Kindeditor();
	
	@Autowired(required = false)
	private ImageConfigService imageConfigService ;
	
	@Autowired
	private FileService fileService;
	
	private Configuration configuration;
	
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public VirtualFile uploadOriginalFile(HttpServletRequest request,HttpServletResponse response,MultipartFile file,String biz,String owner) throws IOException{
		File original = fileService.findReal(biz, owner, FileService.ORIGINAL_FILE);
		FileCopyUtils.copy(file.getInputStream(), FileUtils.openOutputStream(original));
		VirtualFile virtualFile = new VirtualFile();
		virtualFile.setBiz(biz);
		virtualFile.setOwner(owner);
		virtualFile.setContentType(file.getContentType());
		virtualFile.setName(file.getName());
		virtualFile.setSize(file.getSize());
		fileService.saveVirtual(virtualFile);
		return virtualFile;
	}
	
	@RequestMapping(value="/uploadTmp",method=RequestMethod.POST)
	@ResponseBody
	public String uploadTmpFile(HttpServletRequest request,HttpServletResponse response,MultipartFile file,String biz,String owner) throws IOException{
		File tmp = fileService.findReal(biz, owner, FileService.TMP_FILE);
		FileCopyUtils.copy(file.getInputStream(), FileUtils.openOutputStream(tmp));
		return "1";
	}
	
	@RequestMapping(value="/crop",method=RequestMethod.GET)
	public void cropOriginalFileView(String biz,String owner,@RequestParam(value="uploaded",defaultValue="false") boolean uploaded,Model model,Locale locale,HttpServletRequest request,HttpServletResponse response) throws TemplateException, IOException {
		model.addAttribute("uploaded", uploaded);
		if(fileService.findReal(biz, owner, FileService.ORIGINAL_FILE).exists()) {
			model.addAttribute("croped", true);
		}
		Entry<String, ImageConfig> firstEntry = imageConfigService.findImageConfigs(biz).entrySet().iterator().next();
		ImgSize first = firstEntry.getValue().getImgSize();
		model.addAttribute("firtFileName",firstEntry.getKey());
		model.addAttribute("aspectRatio",(double)first.getWidth()/first.getHeight());
		model.addAttribute("ctx", urlPathHelper.getContextPath(request));
		model.addAttribute("biz", biz);
		model.addAttribute("owner", owner);
		response.getWriter().print(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("crop.ftl",locale), model));
	}
	
	@RequestMapping(value="/crop",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cropOriginalFile(String biz,String owner,int left,int top,int width,int height,Model model) throws IOException {
		File tmp = fileService.findReal(biz, owner, FileService.TMP_FILE);
		CommonImage ci = new CommonImage(tmp);
		ci.crop(left, top, width, height).save(fileService.findReal(biz, owner, FileService.ORIGINAL_FILE));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
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
		ImageConfig imgConfig = imageConfigService.findImageConfig(biz,fileName);
		File fileInType = null;
		if(fileName.equals(FileService.TMP_FILE) || imgConfig!=null) {
			//find file in size
			fileInType = fileService.findReal(biz, owner ,fileName);
			if(!fileInType.exists()) {
				File original = fileService.findReal(biz, owner,FileService.ORIGINAL_FILE);
				if(original.exists()) {
					//resize
					ImgSize imgSize =imgConfig.getImgSize();
					new CommonImage(original).resizeWithContainer(imgSize.getWidth(), imgSize.getHeight()).save(fileInType);
				}
			}
		}
		
		if(fileInType == null || !fileInType.exists()) {
			fileInType = imageConfigService.findDefaultImage(biz, fileName);
		}
		
		if(fileInType!=null && fileInType.exists()) {
			FileCopyUtils.copy(new FileInputStream(fileInType), response.getOutputStream());
		}
	}
	
	@RequestMapping(value="/textEditor/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> textEditorUpload(HttpServletRequest request,MultipartFile file){
		try {
			File realFile = fileService.findReal(FileService.BIZ_TEXT_EDITOR, null, file.getOriginalFilename());
			FileCopyUtils.copy(file.getBytes(), realFile);
			return textEditor.onFileUploadSuccess(fileService.getFileHttpPath(REQUEST_PREFIX,FileService.BIZ_TEXT_EDITOR, null, file.getOriginalFilename()));
		} catch (Exception e) {
			return textEditor.onFileUploadFailed(e);
		}
	}
	
	@RequestMapping(value="/textEditor",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> textEditorFiles(String order,String path){
		try{
			File folder = fileService.findBaseFlolder(FileService.BIZ_TEXT_EDITOR);
			return textEditor.listFiles(folder.getPath(),path,order);
		} catch (Exception e) {
			return Collections.emptyMap();
		}
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		FreeMarkerConfigurationFactoryBean fcfb = new FreeMarkerConfigurationFactoryBean();
		fcfb.setTemplateLoaderPaths("classpath:/META-INF");
		fcfb.setDefaultEncoding("UTF-8");
		this.configuration = fcfb.createConfiguration();
	}
}
