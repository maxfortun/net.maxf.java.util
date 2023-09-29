package net.maxf.java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrefixedKeyResourceBundleWrapperTest {
	private Logger logger = Logger.getLogger(PrefixedKeyResourceBundleWrapperTest.class.getName());

	@Test
	public void getWithPrefix() throws Exception {
		ResourceBundle resourceBundle = new PrefixedKeyResourceBundleWrapper(ResourceBundle.getBundle("ApplicationResources")).prefix("prefix/");
		
		String value = resourceBundle.getString("key");

		assertTrue(value.equals("prefixed value"));

	}

}

