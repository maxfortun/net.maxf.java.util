package net.maxf.java.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class EnvResourceBundle extends ResourceBundle {

	@Override
	protected Object handleGetObject(String key) {
		return System.getenv(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(System.getenv().keySet());
	}

}
