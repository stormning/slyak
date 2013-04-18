package com.slyak.core.io;

import org.springframework.web.multipart.MultipartFile;

public interface FilePathGenerator {
	
	boolean supports(String owner);
	
	String generatePath(MultipartFile file,String owner);
}
