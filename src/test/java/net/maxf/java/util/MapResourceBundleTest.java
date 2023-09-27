package net.maxf.java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapResourceBundleTest {
	private Logger logger = Logger.getLogger(MapResourceBundleTest.class.getName());

	@Test
	public void testMapResourceBundleKeys() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("mapKey1", "value1");
		map.put("mapKey2", "value2");

		ResourceBundle resourceBundle = new MapResourceBundle(map);
		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		while(keyEnumeration.hasMoreElements()) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}

	}

	@Test
	public void testMapResourceBundleChange() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("mapKey1", "value1");
		map.put("mapKey2", "value2");

		ResourceBundle resourceBundle = new MapResourceBundle(map);
		
		map.put("mapKey2", "value2-changed");

		String value = resourceBundle.getString("mapKey2");

		assertTrue(value.equals("value2-changed"));

	}

}

