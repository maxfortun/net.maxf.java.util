package net.maxf.java.util.hashicorp.vault;

import java.util.Collections;
import java.util.List;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.springframework.vault.core.VaultKeyValueOperations;

import net.maxf.java.util.PrefixedKeyResourceBundle;

public class VaultResourceBundle extends PrefixedKeyResourceBundle {

	private String valueField = "value";
	private String root = null;

	private VaultKeyValueOperations vaultKeyValueOperations;

	public VaultResourceBundle(VaultKeyValueOperations vaultKeyValueOperations) {
		this.vaultKeyValueOperations = vaultKeyValueOperations;
	}

	public VaultResourceBundle root(String root) {
		this.root = root;
		return this;
	}

	public String root() {
		return root;
	}

	public VaultResourceBundle valueField(String valueField) {
		this.valueField = valueField;
		return this;
	}

	public String valueField() {
		return valueField;
	}

	@Override
	protected Object handleGetObject(String key) {
		String fullKey = prefixedKey(key);

		if(null != root) {
			fullKey = root+fullKey;
		}

		try {
			return vaultKeyValueOperations.get(key).getData().get(valueField).toString();
		} catch(Throwable t) {
			MissingResourceException missingResourceException = new MissingResourceException(
				"Can't find resource for bundle " + this.getClass().getName() + ", key " + fullKey,
				this.getClass().getName(),
				fullKey);
			missingResourceException.addSuppressed(t);
			throw missingResourceException;
		}
	}

	private class KeyEnumeration implements Enumeration<String> {

		private String path = root;
		private List<String> keys = null;

		public KeyEnumeration() {

			if(null == path) {
				path = prefix();
			} else {
				path += prefix();
			}

			if(null == path) {
				path = "/";
			}
			
			list();
		}
	
		private void list() {
			keys = vaultKeyValueOperations.list(path);
		}
	
		public boolean hasMoreElements() {
			return (keys.size() > 0);
		}
					
		public String nextElement() {
			if(keys.size() == 0) {
				throw new NoSuchElementException();
			}

			return keys.remove(0);
		}
	}
	

	@Override
	public Enumeration<String> getKeys() {
		return prefixedKeyEnumeration(new KeyEnumeration());
	}

}
