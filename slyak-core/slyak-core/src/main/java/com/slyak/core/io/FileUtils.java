package com.slyak.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.springframework.web.util.WebUtils;

public class FileUtils {

	public static String readFileToString(String relativePath,ServletContext context) throws IOException{
		InputStream is = null;
		BufferedReader br = null;
		try{
			is = context.getResourceAsStream(relativePath);
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			StringBuffer sb = new StringBuffer();
			char[] cbuf = new char[1024];
			int sz = 0;
			while((sz = br.read(cbuf))!=-1){
				sb.append(cbuf,0,sz);
				cbuf = new char[1024];
			}
			return sb.toString();
		} finally {
			if(br!=null){
				br.close();
			}
			if(is!=null){
				is.close();
			}
		}
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
