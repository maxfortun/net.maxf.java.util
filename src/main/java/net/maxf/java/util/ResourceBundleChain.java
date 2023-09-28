package net.maxf.java.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ResourceBundleChain extends PrefixedKeyResourceBundle {
	private List<ResourceBundle> resourceBundles;

	public ResourceBundleChain(List<ResourceBundle> resourceBundles) {
		this.resourceBundles = resourceBundles;
	}

	@Override
	protected Object handleGetObject(String key) {
		String prefixedKey = prefixedKey(key);

		ArrayList<Throwable> suppressed = new ArrayList<>();
		ArrayList<String> bundles = new ArrayList<>();

		try {
			for(ResourceBundle resourceBundle : resourceBundles) {
				try {
					Object object = resourceBundle.getObject(prefixedKey);
					if(null != object) {
						return object;
					}
				} catch(Throwable t) {
					suppressed.add(t);
					bundles.add(resourceBundle.getClass().getName());
				}
			}
		} catch(Throwable t) {
			suppressed.add(t);
		} 

		
		MissingResourceException missingResourceException = new MissingResourceException(
			"Can't find resource for bundles " + String.join(",", bundles) + ", key " + prefixedKey,
			this.getClass().getName(),
			prefixedKey);

		for(Throwable t : suppressed) {
			missingResourceException.addSuppressed(t);
		}

		throw missingResourceException;
	}

	private class KeyEnumeration implements Enumeration<String> {

		private boolean hasMoreElements = false;

		private int currentResourceBundleIndex = 0;
		private Enumeration<String> currentResourceBundleKeyEnumeration = resourceBundles.get(currentResourceBundleIndex).getKeys();

		public KeyEnumeration() {
			nextNonEmptyBundle();
		}

		private void nextNonEmptyBundle() {

			if(currentResourceBundleKeyEnumeration.hasMoreElements()) {
				hasMoreElements = true;
				return;
			}

			for(currentResourceBundleIndex++; currentResourceBundleIndex < resourceBundles.size(); currentResourceBundleIndex++ ) {
				currentResourceBundleKeyEnumeration = resourceBundles.get(currentResourceBundleIndex).getKeys();
				if(currentResourceBundleKeyEnumeration.hasMoreElements()) {
					hasMoreElements = true;
					return;
				}
			}

			hasMoreElements = false;
		}

		public boolean hasMoreElements() {
			return hasMoreElements;
		}

		public String nextElement() {
			if(!hasMoreElements) {
				throw new NoSuchElementException();
			}

			String nextElement = currentResourceBundleKeyEnumeration.nextElement();

			if(!currentResourceBundleKeyEnumeration.hasMoreElements()) {
				nextNonEmptyBundle();
			}

			return nextElement;
		}
	}

	@Override
	public Enumeration<String> getKeys() {
		return new PrefixedKeyEnumeration(new KeyEnumeration());
	}

}
