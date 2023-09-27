package net.maxf.java.util;

import java.util.Collections;
import java.util.Map;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MapResourceBundle extends ResourceBundle {

	protected Map<String, String> map;

	public MapResourceBundle(Map<String, String> map) {
		this.map = map;
	}

	@Override
	protected Object handleGetObject(String key) {
		return map.get(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(map.keySet());
	}

}
