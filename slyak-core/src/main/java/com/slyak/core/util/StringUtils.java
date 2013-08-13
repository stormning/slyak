package com.slyak.core.util;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

	private static final String CHINESE_PATTERN = "[\u4e00-\u9fa5]";
	
	private static final String IMG_PATTERN = "img.*\\W?src\\s*=[\"']{1}?([^\"']+?)[\"']{1}?";

	public static String cut(String sourceString, int byteSize) {
		if(isEmpty(sourceString)){
			return null;
		}
		sourceString = sourceString.replaceAll("<style[^>]*?>[\\s\\S]*?</style>|<script[^>]*?>[\\s\\S]*?</script>|<.+?>", "");
		if(isEmpty(sourceString)){
			return null;
		}
		
		int count = 0;
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sourceString.length(); i++) {
			String temp = String.valueOf(sourceString.charAt(i));
			if (temp.matches(CHINESE_PATTERN)) {  
				count += 2;
            } else {
            	count +=1;
            }
			if(byteSize>=count){
				sb.append(temp);
			}
		}
		return sb.toString();
	}
	
	public static Set<String> findImgSrcs(String htmlString){
		Set<String> imgSrcs = new LinkedHashSet<String>();
		Pattern imagePattern = Pattern.compile(IMG_PATTERN);
		Matcher imgMatcher = imagePattern.matcher(htmlString);
		while (imgMatcher.find()) {
			String src = org.apache.commons.lang.StringUtils.trimToNull(imgMatcher.group(1));
			if(src!=null){
				imgSrcs.add(imgMatcher.group(1));
			}
		}
		return imgSrcs;
	}
	
	public static String preparePath(String path){
		if(path==null){
			return org.apache.commons.lang.StringUtils.EMPTY;
		}
		path = org.apache.commons.lang.StringUtils.replaceEach(path, new String[]{"/","\\"}, new String[]{File.separator,File.separator});
		if(!path.startsWith(File.separator)&&!path.startsWith("file")){
			path = File.separator+path;
		}
		return path;
	}
	
	public static void main(String[] args) {
		//<img class="current" src="http://f.any123.com/pagelet/ad/index/Banner1.jpg" />aaa<img src="">
		//<img data-ke-src="http://static.oschina.net/uploads/img/201211/13065445_YtoG.png" src="http://static.oschina.net/uploads/img/201211/13065445_YtoG.png" alt="eXo Platform">
	}
}
