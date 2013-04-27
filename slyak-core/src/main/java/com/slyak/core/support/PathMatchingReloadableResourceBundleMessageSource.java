package com.slyak.core.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class PathMatchingReloadableResourceBundleMessageSource extends
		ReloadableResourceBundleMessageSource {
	
	private static final String PROPERTIES_SUFFIX = "*.properties";
	
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		super.setResourceLoader(new FileSystemResourceLoader());
	}

	@Override
	public void setBasenames(String... basenames) {
		try{
			List<String> resourceNames = new ArrayList<String>(); 
			for (String basename : basenames) {
				Resource[] resources = resourcePatternResolver.getResources(basename+PROPERTIES_SUFFIX);
				for (Resource resource : resources) {
					resourceNames.add(resource.getURL().getPath().replace(".properties", ""));
				}
			}
			super.setBasenames(resourceNames.toArray(new String[resourceNames.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
