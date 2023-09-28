package net.maxf.java.util;

import java.util.Collections;
import java.util.Map;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class PrefixedKeyResourceBundleWrapper extends PrefixedKeyResourceBundle {

	ResourceBundle resourceBundle;

	public PrefixedKeyResourceBundleWrapper(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	@Override
	protected Object handleGetObject(String key) {
		return resourceBundle.getObject(prefixedKey(key));
	}

	@Override
	public Enumeration<String> getKeys() {
		return prefixedKeyEnumeration(resourceBundle.getKeys());
	}

}
