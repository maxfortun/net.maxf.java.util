package net.maxf.java.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceBundleChainTest {
	private Logger logger = Logger.getLogger(ResourceBundleChainTest.class.getName());

	@Test
	public void testResourceBundleChainKeys() throws Exception {
		ResourceBundle envResourceBundle = new EnvResourceBundle();

		Map<String, String> map = new HashMap<>();
		map.put("mapKey1", "mapValue1");
		map.put("mapKey2", "mapValue2");
		ResourceBundle mapResourceBundle = new MapResourceBundle(map);

		ResourceBundle emptyMapResourceBundle = new MapResourceBundle(new HashMap<String, String>());

		ResourceBundle fileResourceBundle = ResourceBundle.getBundle("ApplicationResources");

		List<ResourceBundle> list = Arrays.asList(new ResourceBundle[]{ envResourceBundle, mapResourceBundle, emptyMapResourceBundle, fileResourceBundle});

		logger.fine("ResourceBundleChain of "+list.size()+" bundles");

		ResourceBundle resourceBundle = new ResourceBundleChain(list);

		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		while(keyEnumeration.hasMoreElements()) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}
	}

	@Test
	public void testResourceBundleChainGet() throws Exception {
		ResourceBundle envResourceBundle = new EnvResourceBundle();

		Map<String, String> map = new HashMap<>();
		map.put("mapKey1", "mapValue1");
		map.put("mapKey2", "mapValue2");
		ResourceBundle mapResourceBundle = new MapResourceBundle(map);

		ResourceBundle emptyMapResourceBundle = new MapResourceBundle(new HashMap<String, String>());

		ResourceBundle fileResourceBundle = ResourceBundle.getBundle("ApplicationResources");

		List<ResourceBundle> list = Arrays.asList(new ResourceBundle[]{ envResourceBundle, mapResourceBundle, emptyMapResourceBundle, fileResourceBundle});

		logger.fine("ResourceBundleChain of "+list.size()+" bundles");

		ResourceBundle resourceBundle = new ResourceBundleChain(list);


		assertTrue(resourceBundle.getString("env_bundle").equals("env"));
		assertTrue(resourceBundle.getString("mapKey2").equals("mapValue2"));
		assertTrue(resourceBundle.getString("key1").equals("file"));
	}

}

