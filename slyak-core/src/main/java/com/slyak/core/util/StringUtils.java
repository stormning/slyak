/**
 * Project name : slyak-core
 * File name : StringUtils.java
 * Package name : com.slyak.core.util
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.util;

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
		sourceString = cleanHtml(sourceString);
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
	
	public static String cleanHtml(String html){
		return html.replaceAll("<style[^>]*?>[\\s\\S]*?</style>|<script[^>]*?>[\\s\\S]*?</script>|<.+?>", "");
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
	
	public static String devidePath(String longPath,String seperator){
		int len = longPath.length();
		int start = 0;
		StringBuffer sb = new StringBuffer();
		int step = 2;
		while(start<len){
			if(len-start<step){
				step = len-start;
			}
			sb.append(seperator+longPath.substring(start,start+step));
			start+=2;
		}
		return sb.toString();
	}
	
	public static String randomName() {
		String[] simple = { "an", "ao", "andy", "apple", "blue", "boy", "ben",
				"bob", "black", "cold", "code", "color", "cream", "cindy",
				"doctor", "dany", "dog", "dick", "egg", "easy", "editor",
				"fox", "fade", "fall", "fancy", "green", "gentle", "good",
				"glass", "happy", "half", "hard", "holidy", "jack", "jade",
				"jeep", "july", "kid", "kind", "king", "killer", "lucky",
				"luck", "looser", "lord", "mm", "moon", "magic", "music",
				"note", "noah", "nil", "nut", "orange", "open", "ok", "old",
				"pop", "park", "painter", "print", "queen", "quite", "quake",
				"quail", "red", "rapid", "rabbit", "rose", "soul", "saga",
				"sailor", "soldier", "tiger", "tag", "trick", "touch", "UFO",
				"guly", "under", "user", "victor", "visitor", "visual", "void",
				"world", "wise", "wide", "water", "x-ray", "x", "yellow",
				"year", "yolk", "yew", "zink", "zealot", "zebra", "zero" };
		return simple[(int) Math.floor(Math.random() * (simple.length))];
	}
	
	public static void main(String[] args) {
		//<img class="current" src="http://f.any123.com/pagelet/ad/index/Banner1.jpg" />aaa<img src="">
		//<img data-ke-src="http://static.oschina.net/uploads/img/201211/13065445_YtoG.png" src="http://static.oschina.net/uploads/img/201211/13065445_YtoG.png" alt="eXo Platform">
	}
}
