package net.maxf.java.util.hashicorp.vault;

import java.net.URI;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;

import org.springframework.vault.client.VaultEndpoint;

import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.NamespaceScopedVaultTemplate;

import org.springframework.vault.support.VaultToken;

public class VaultResourceBundleTest {
	private Logger logger = Logger.getLogger(VaultResourceBundleTest.class.getName());
	
	private String vaultAddr;
	private String vaultToken;
	private String vaultNamespace;

	public VaultResourceBundleTest() {
		vaultAddr = System.getenv("VAULT_ADDR");
		vaultToken = System.getenv("VAULT_TOKEN");
		vaultNamespace = System.getenv("VAULT_NAMESPACE");
	}

	boolean vaultAvailable() {
		return vaultAddr != null && vaultToken != null;
	}

	@Test
	@EnabledIf("vaultAvailable")
	public void testVaultResourceBundleTestGetKeys() throws Exception {
		ClientAuthentication clientAuthentication = new TokenAuthentication(VaultToken.of(vaultToken));
		VaultTemplate vaultTemplate = new NamespaceScopedVaultTemplate(VaultEndpoint.from(new URI(vaultAddr)), clientAuthentication, vaultNamespace);
		VaultKeyValueOperations vaultKeyValueOperations = vaultTemplate.opsForKeyValue("secret", KeyValueBackend.unversioned());

		ResourceBundle resourceBundle = new VaultResourceBundle(vaultKeyValueOperations);

		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		for(int i = 0; keyEnumeration.hasMoreElements() && i < 20; i++) {
			String key = keyEnumeration.nextElement();
			if(key.endsWith("/")) {
				logger.fine(key);
			} else {
				String value = resourceBundle.getString(key);
				logger.fine(key+"="+value);
			}
		}
	}

}

