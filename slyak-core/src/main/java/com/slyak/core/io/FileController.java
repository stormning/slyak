package com.slyak.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/file")
public class FileController implements ServletContextAware,ApplicationContextAware,InitializingBean{
	
	private ServletContext servletContext;
	
	private ApplicationContext applicationContext;
	
	private static final String DEFAULT_UPLOAD_PATH="/WEB-INF/upload";
	
	private int cacheSeconds = 31556926;
	
	private String uploadPath = DEFAULT_UPLOAD_PATH;
	
	private TextEditor textEditor = new Kindeditor();
	
	
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
			String rootPath = WebUtils.getRealPath(servletContext,preparePath(uploadPath)+"/textEditor");
			return textEditor.listFiles(rootPath,path,order);
		} catch (Exception e) {
			return Collections.EMPTY_MAP;
		}
	}
	
	private String uploadAndGetUrl(MultipartFile file,String owner) throws IOException{
		CommonFile cf = new CommonFile(file.getInputStream());
		String relativePath = generatePath(file,owner);
		String realPath = servletContext.getRealPath(preparePath(uploadPath))+relativePath;
		cf.save(realPath);
		//url for web
		return "/file"+relativePath.replace('\\', '/');
	}
	
	private String generatePath(MultipartFile file,String owner){
		if(owner == null){
			return generateDataFolder()+File.separator+file.getOriginalFilename();
		}else{
			return preparePath(generatePathByOwner(file,owner));
		}
	}
	
	private String preparePath(String path){
		if(path==null){
			return StringUtils.EMPTY;
		}
		path = StringUtils.replaceEach(path, new String[]{"/","\\"}, new String[]{File.separator,File.separator});
		if(!path.startsWith(File.separator)){
			path = File.separator+path;
		}
		return path;
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

	private void initStaticResourceHandlerMapping(){
		Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<String, HttpRequestHandler>();
		ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
		requestHandler.setLocations(Collections.singletonList(applicationContext.getResource(preparePath(uploadPath)+"/")));
		requestHandler.setCacheSeconds(cacheSeconds);
		requestHandler.setServletContext(servletContext);
		requestHandler.setApplicationContext(applicationContext);
		urlMap.put("/file/**", requestHandler);
		
		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setUrlMap(urlMap);
		applicationContext.getAutowireCapableBeanFactory().initializeBean(handlerMapping, null);
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;  
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		defaultListableBeanFactory.registerSingleton("fileResourceHandlerMapping", handlerMapping);
		
		//refresh context
		applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initStaticResourceHandlerMapping();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.replaceEach("/WEB-INF\\a", new String[]{"/","\\"}, new String[]{File.separator,File.separator}));
	}
}
