package net.maxf.java.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.maxf.java.util.EnvResourceBundle;

public class EnvResourceBundleTest {
	private Logger logger = Logger.getLogger(EnvResourceBundleTest.class.getName());

	@Test
	public void testEnvResourceBundleKeys() throws Exception {
		ResourceBundle resourceBundle = new EnvResourceBundle();
		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		while(keyEnumeration.hasMoreElements()) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}
	}

	@Test
	public void testEnvResourceBundleGet() throws Exception {
		ResourceBundle resourceBundle = new EnvResourceBundle();
		// env_bundle is set in pom.xml
		String key = "env_bundle";
		String value = resourceBundle.getString(key);
		logger.fine(key+"="+value);
		assertTrue(value.equals("env"));
	}

}

