package net.maxf.java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CachingResourceBundleTest {
	private Logger logger = Logger.getLogger(CachingResourceBundleTest.class.getName());

	@Test
	public void testCachingResourceBundleCache() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("key", "value1");

		long ttl = 1000;
		ResourceBundle resourceBundle = new CachingResourceBundle(new MapResourceBundle(map), ttl);

		String value = resourceBundle.getString("key");

		assertTrue(value.equals("value1"));
		
		map.put("key", "value2");

		assertTrue(value.equals("value1"));

		Thread.sleep(ttl);
			
		value = resourceBundle.getString("key");
		assertTrue(value.equals("value2"));
	}

}

