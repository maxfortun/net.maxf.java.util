package net.maxf.java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrefixedKeyResourceBundleTest {
	private Logger logger = Logger.getLogger(PrefixedKeyResourceBundleTest.class.getName());

	@Test
	public void getWithPrefix() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("key", "value");
		map.put("prefix/key", "prefixed value");

		ResourceBundle resourceBundle = new MapResourceBundle(map).prefix("prefix/");
		
		String value = resourceBundle.getString("key");

		assertTrue(value.equals("prefixed value"));

	}

}

