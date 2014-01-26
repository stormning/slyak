/**
 * Project name : slyak-core
 * File name : Kindeditor.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;


@SuppressWarnings({"rawtypes","unchecked"})
public class Kindeditor implements TextEditor {

	private String[] imgSuffix = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
	
	@Override
	public Map<String, Object> onFileUploadSuccess(String url) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("error", 0);
		result.put("url", url);
		return result;
	}

	@Override
	public Map<String, Object> onFileUploadFailed(Exception e) {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("error", 1);
		result.put("message", e.getMessage());
		return result;
	}

	@Override
	public Map<String, Object> listFiles(String rootPath,String dir,String order) {
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		try {
			File directory = ResourceUtils.getFile(rootPath+File.separator+dir.replaceFirst("r", ""));
			for (File file : directory.listFiles()) {
				Hashtable hash = new Hashtable();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = FilenameUtils.getExtension(fileName);
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(imgSuffix).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
			
			if ("size".equals(order)) {
				Collections.sort(fileList, new SizeComparator());
			} else if ("type".equals(order)) {
				Collections.sort(fileList, new TypeComparator());
			} else {
				Collections.sort(fileList, new NameComparator());
			}
			Map<String,Object> result = new HashMap<String,Object>();
			
			int pos = directory.getPath().indexOf("textEditor");
			
			int currentDirPathPos = pos+9;
			
			String currentDirPath = directory.getPath().substring(currentDirPathPos).replace('\\', '/');
			
			String[] splited = currentDirPath.split("/");
			result.put("moveup_dir_path", splited.length>1?splited[0]:"");
			result.put("current_dir_path", currentDirPath);
			result.put("current_url", ("/file/"+directory.getPath().substring(pos)+"/").replace('\\', '/'));
			result.put("total_count", fileList.size());
			result.put("file_list", fileList);
			return result;
		} catch (FileNotFoundException e) {
			return Collections.emptyMap();
		}
	}
	
	private class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	private class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
	private class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
	}

}
