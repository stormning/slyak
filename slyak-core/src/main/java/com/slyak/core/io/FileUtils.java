package com.slyak.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.WebUtils;

public class FileUtils {

	public static String readFileToString(String relativePath,ServletContext context) throws IOException{
		InputStream is = context.getResourceAsStream(relativePath);
		return IOUtils.toString(is);
	}
	
	public static void writeStringToFile(String string,String relativePath,ServletContext context) throws IOException{
		String filePath = WebUtils.getRealPath(context, relativePath);
		org.apache.commons.io.FileUtils.writeStringToFile(new File(filePath), string,false);
	}
	
	public static File getFile(String relativePath,ServletContext context) throws FileNotFoundException{
		String filePath = WebUtils.getRealPath(context, relativePath);
		return new File(filePath);
	}
}
