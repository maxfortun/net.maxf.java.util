package net.maxf.java.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class CachingResourceBundle extends ResourceBundle {

	private Map<String, CachedResource> cache = new HashMap<>();

	ResourceBundle resourceBundle;
	private long ttl;

	public CachingResourceBundle(ResourceBundle resourceBundle, long ttl) {
		this.resourceBundle = resourceBundle;
		this.ttl = ttl;
	}

	private class CachedResource {
		private Date expiration;
		private Object object;

		public CachedResource(Object object, long ttl) {
			this.object = object;
			this.expiration = new Date(new Date().getTime()+ttl);
		}

		public Object object() {
			return object;
		}

		public boolean expired() {
			Date now = new Date();
			return now.getTime() > expiration.getTime();
		}
	}

	@Override
	protected Object handleGetObject(String key) {
		CachedResource resource = cache.get(key);

		if(null == resource || resource.expired()) {
			resource = new CachedResource(resourceBundle.getObject(key), ttl);
		}
		
		return resource.object();
	}

	@Override
	public Enumeration<String> getKeys() {
		return resourceBundle.getKeys();
	}

}
