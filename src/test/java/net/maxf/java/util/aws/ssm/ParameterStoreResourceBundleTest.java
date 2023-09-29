package net.maxf.java.util.aws.ssm;

import software.amazon.awssdk.services.sts.StsClient;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.ssm.SsmClient;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterStoreResourceBundleTest {
	private Logger logger = Logger.getLogger(ParameterStoreResourceBundleTest.class.getName());
	
	private SsmClient ssmClient = null;
	
	public ParameterStoreResourceBundleTest() {
		AwsCredentialsProvider awsCredentialsProvider = DefaultCredentialsProvider.create();

		Region region = DefaultAwsRegionProviderChain.builder().build().getRegion();

		if(null == region) {
			String regionName = System.getenv("AWS_REGION");
			region = Region.of(regionName);
		}

		StsClient stsClient = StsClient.create();

		try {
			stsClient.getCallerIdentity().account();
			ssmClient = SsmClient.create();
		} catch(Exception e) {
			logger.log(Level.WARNING, "Not logged in into AWS", e);
		}
	}

	boolean awsAvailable() {
		return ssmClient != null;
	}

	@Test
	@EnabledIf("awsAvailable")
	public void testParameterStoreResourceBundleTestGetKeys() throws Exception {
		ResourceBundle resourceBundle = new ParameterStoreResourceBundle(ssmClient).maxResults(10);

		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		for(int i = 0; keyEnumeration.hasMoreElements() && i < 20; i++) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}
	}

}

