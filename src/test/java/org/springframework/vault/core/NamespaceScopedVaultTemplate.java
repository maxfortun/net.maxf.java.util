/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.vault.core;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.SessionManager;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.client.VaultEndpointProvider;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * @author Mark Paluch 
 * https://gist.github.com/mp911de/e2480afd4d5d5c27038e2b1b49b30cfa
 */
public class NamespaceScopedVaultTemplate extends VaultTemplate {
	private Logger logger = Logger.getLogger(NamespaceScopedVaultTemplate.class.getName());

	private final String namespace;

	public NamespaceScopedVaultTemplate(VaultEndpoint vaultEndpoint,
										ClientAuthentication clientAuthentication, String namespace) {
		super(vaultEndpoint, clientAuthentication);
		this.namespace = namespace;
	}

	public NamespaceScopedVaultTemplate(VaultEndpoint vaultEndpoint,
										ClientHttpRequestFactory clientHttpRequestFactory,
										SessionManager sessionManager, String namespace) {
		super(vaultEndpoint, clientHttpRequestFactory, sessionManager);
		this.namespace = namespace;
	}

	public NamespaceScopedVaultTemplate(VaultEndpointProvider endpointProvider,
										ClientHttpRequestFactory requestFactory, SessionManager sessionManager,
										String namespace) {
		super(endpointProvider, requestFactory, sessionManager);
		this.namespace = namespace;
	}

	/**
	 * Associate namespace interceptor that adds the namespace header to each Vault
	 * request.
	 *
	 * @param endpointProvider must not be {@literal null}.
	 * @param requestFactory must not be {@literal null}.
	 * @return
	 */
	@Override
	protected RestTemplate doCreateRestTemplate(VaultEndpointProvider endpointProvider,
			ClientHttpRequestFactory requestFactory) {

		RestTemplate restTemplate = super.doCreateRestTemplate(endpointProvider,
				requestFactory);

		restTemplate.getInterceptors().add((request, body, execution) -> {

			logger.fine("Adding header X-Vault-Namespace: "+namespace);
			request.getHeaders().add("X-Vault-Namespace", namespace);

			return execution.execute(request, body);
		});

		return restTemplate;
	}

	@Override
	protected RestTemplate doCreateSessionTemplate(VaultEndpointProvider endpointProvider,
			ClientHttpRequestFactory requestFactory) {

		RestTemplate restTemplate = super.doCreateSessionTemplate(endpointProvider,
				requestFactory);

		restTemplate.getInterceptors().add((request, body, execution) -> {

			logger.fine("Adding header X-Vault-Namespace: "+namespace);
			request.getHeaders().add("X-Vault-Namespace", namespace);

			return execution.execute(request, body);
		});

		return restTemplate;
	}
}
