package net.maxf.java.util.aws;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParametersRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersResponse;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;

import software.amazon.awssdk.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import net.maxf.java.util.PrefixedKeyResourceBundle;

public class ParameterStoreResourceBundle extends PrefixedKeyResourceBundle {
	private SsmClient ssmClient;

	private String root = null;

	public ParameterStoreResourceBundle(SsmClient ssmClient) {
		this.ssmClient = ssmClient;
	}

	public ParameterStoreResourceBundle root(String root) {
		this.root = root;
		return this;
	}

	public String root() {
		return root;
	}

	@Override
	protected Object handleGetObject(String key) {
		String fullKey = prefixedKey(key);

		if(null != root) {
			fullKey = root+fullKey;
		}

		GetParametersRequest getParametersRequest = GetParametersRequest.builder().names(fullKey).withDecryption(true).build();
		GetParametersResponse getParametersResponse = ssmClient.getParameters(getParametersRequest);

		for (Parameter parameter : getParametersResponse.parameters()) {
			return parameter.value();
		}

		throw new MissingResourceException(
			"Can't find resource for bundle " + this.getClass().getName() + ", key " + fullKey,
			this.getClass().getName(),
			fullKey);

	}

	private class KeyEnumeration implements Enumeration<String> {

		private String path = root;
		private String nextToken = null;
		private List<Parameter> parameters = null;

		public KeyEnumeration() {
			if(null == path) {
				path = prefix();
			} else {
				path += prefix();
			}

			if(null == path) {
				path = "/";
			}
			
			nextBatch();
		}

		private void nextBatch() {
			GetParametersByPathRequest request = GetParametersByPathRequest.builder().path(path).recursive(true).nextToken(nextToken).maxResults(50).build();
			GetParametersByPathResponse response = ssmClient.getParametersByPath(request);
			nextToken = response.nextToken();
			parameters = response.parameters();
		}

		public boolean hasMoreElements() {
			return (parameters.size() > 0);
		}
					
		public String nextElement() {
			if(parameters.size() == 0) {
				throw new NoSuchElementException();
			}
			Parameter parameter = parameters.remove(0);
			if(parameters.size() == 0 && null != nextToken) {
				nextBatch();
			}
			return parameter.name();
		}
	}

	@Override
	public Enumeration<String> getKeys() {
		return prefixedKeyEnumeration(new KeyEnumeration());
	}

}
