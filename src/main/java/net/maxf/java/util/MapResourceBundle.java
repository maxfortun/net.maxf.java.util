package net.maxf.java.util;

import java.util.Collections;
import java.util.Map;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MapResourceBundle extends PrefixedKeyResourceBundle {

	private Map<String, String> map;

	public MapResourceBundle(Map<String, String> map) {
		this.map = map;
	}

	@Override
	protected Object handleGetObject(String key) {
		return map.get(prefixedKey(key));
	}

	@Override
	public Enumeration<String> getKeys() {
		return prefixedKeyEnumeration(Collections.enumeration(map.keySet()));
	}

}
