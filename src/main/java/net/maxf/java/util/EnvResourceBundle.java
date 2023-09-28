package net.maxf.java.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class EnvResourceBundle extends PrefixedKeyResourceBundle {

	@Override
	protected Object handleGetObject(String key) {
		return System.getenv(prefixedKey(key));
	}

	@Override
	public Enumeration<String> getKeys() {
		return prefixedKeyEnumeration(Collections.enumeration(System.getenv().keySet()));
	}

}
