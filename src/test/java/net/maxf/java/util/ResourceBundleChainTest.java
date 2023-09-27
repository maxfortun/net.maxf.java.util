package net.maxf.java.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
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
		ResourceBundle mapResourceBundle = new MapResourceBundle(map);

		ResourceBundle fileResourceBundle = ResourceBundle.getBundle("ApplicationResources");

		ResourceBundle resourceBundle = new ResourceBundleChain(Arrays.asList(new ResourceBundle[]{ envResourceBundle, mapResourceBundle, fileResourceBundle}));

		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		while(keyEnumeration.hasMoreElements()) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}
	}

	@Test
	public void testResourceBundleChainGet() throws Exception {
	}

}

