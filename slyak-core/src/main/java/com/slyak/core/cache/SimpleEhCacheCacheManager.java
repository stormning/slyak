package com.slyak.core.cache;

import net.sf.ehcache.Ehcache;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class SimpleEhCacheCacheManager extends EhCacheCacheManager {

	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			// check the EhCache cache again
			// (in case the cache was added at runtime)
			Ehcache ehcache = getCacheManager().addCacheIfAbsent(name);
			if (ehcache != null) {
				cache = new EhCacheCache(ehcache);
				addCache(cache);
			}
		}
		return cache;
	}

}
