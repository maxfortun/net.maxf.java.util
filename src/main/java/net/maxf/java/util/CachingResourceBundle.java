package net.maxf.java.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class CachingResourceBundle extends ResourceBundle {

	private Map<String, CachedResource> cache = new HashMap<>();

	ResourceBundle resourceBundle;
	private long ttl = -1; // Forever
	private boolean useStaleResources = false;

	public CachingResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public CachingResourceBundle ttl(long ttl) {
		this.ttl = ttl;
		return this;
	}
	
	public long ttl() {
		return ttl;
	}

	public CachingResourceBundle useStaleResources(boolean useStaleResources) {
		this.useStaleResources = useStaleResources;
		return this;
	}

	public boolean useStaleResources() {
		return useStaleResources;
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

		public boolean stale() {
			if(ttl < 0) {
				return false;
			}

			Date now = new Date();
			return now.getTime() > expiration.getTime();
		}
	}

	@Override
	protected Object handleGetObject(String key) {
		CachedResource resource = cache.get(key);

		if(null == resource || resource.stale()) {
			try {
				resource = new CachedResource(resourceBundle.getObject(key), ttl);
			} catch(Exception e) {
				if(null == resource || !useStaleResources) {
					throw e;
				}
			}
		}
		
		return resource.object();
	}

	@Override
	public Enumeration<String> getKeys() {
		return resourceBundle.getKeys();
	}

}
