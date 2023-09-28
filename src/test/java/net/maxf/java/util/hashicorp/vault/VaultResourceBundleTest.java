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

	@Test
	public void testVaultResourceBundleTestGetKeys() throws Exception {
		ClientAuthentication clientAuthentication = new TokenAuthentication(VaultToken.of(System.getProperty("VAULT_TOKEN")));
		VaultTemplate vaultTemplate = new NamespaceScopedVaultTemplate(VaultEndpoint.from(new URI(System.getProperty("VAULT_ADDR"))), clientAuthentication, System.getProperty("VAULT_NAMESPACE"));
		VaultKeyValueOperations vaultKeyValueOperations = vaultTemplate.opsForKeyValue("secret", KeyValueBackend.unversioned());

		ResourceBundle resourceBundle = new VaultResourceBundle(vaultKeyValueOperations);

		Enumeration<String> keyEnumeration = resourceBundle.getKeys();
		for(int i = 0; keyEnumeration.hasMoreElements() && i < 20; i++) {
			String key = keyEnumeration.nextElement();
			String value = resourceBundle.getString(key);
			logger.fine(key+"="+value);
		}
	}

}

