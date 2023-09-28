package net.maxf.java.util;

import java.util.Enumeration;
import java.util.ResourceBundle;

public abstract class PrefixedKeyResourceBundle extends ResourceBundle {

	private String prefix;

	public PrefixedKeyResourceBundle prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public String prefix() {
		return prefix;
	}

	protected String prefixedKey(String key) {
		if(null == prefix) {
			return key;
		}
		return prefix+key;
	}

	private String unprefixedKey(String key) {
		if(null == prefix) {
			return key;
		}
		return key.replaceAll("^"+prefix,"");
	}

	protected class PrefixedKeyEnumeration implements Enumeration<String> {
		private Enumeration<String> enumeration;

		protected PrefixedKeyEnumeration(Enumeration<String> enumeration) {
			this.enumeration = enumeration;
		}

		public boolean hasMoreElements() {
			return enumeration.hasMoreElements();
        }

        public String nextElement() {
			return unprefixedKey(enumeration.nextElement());
		}
	}
}
