# net.maxf.java.util

Extending java.util classes for that bit of extra convenience :)  

## [ResourceBundle](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/ResourceBundle.html)
Hardcoding is bad. Taking configuration `out of the code` is better. Taking configuration `out of the deployment` is best.  
Technical debt is costly. Refactoring is expensive. Retesting is time consuming.  
Reusing the same interfaces and replacing them with a more modern implementation is minimizing the blast radius.  
Keep the apps as they are and move the configuration out of them. Here are some examples how.  

Class | Test | Description 
--- | --- | ---
[EnvResourceBundle](src/main/java/net/maxf/java/util/EnvResourceBundle.java) | [EnvResourceBundleTest](src/test/java/net/maxf/java/util/EnvResourceBundleTest.java) | Retrieves resources from the environment variables. Useful for runtimes that automatically provide configuration.
[MapResourceBundle](src/main/java/net/maxf/java/util/MapResourceBundle.java) | [MapResourceBundleTest](src/test/java/net/maxf/java/util/MapResourceBundleTest.java) | General purpose Map wrapper. Useful with any data provider that returns a map.
[PrefixedKeyResourceBundle](src/main/java/net/maxf/java/util/PrefixedKeyResourceBundle.java) | [PrefixedKeyResourceBundleTest](src/test/java/net/maxf/java/util/PrefixedKeyResourceBundleTest.java) | Base class. Adds a key prefix. Useful when working with large resource bundles, but only need a subset of keys.
[PrefixedKeyResourceBundleWrapper](src/main/java/net/maxf/java/util/PrefixedKeyResourceBundleWrapper.java) | [PrefixedKeyResourceBundleWrapperTest](src/test/java/net/maxf/java/util/PrefixedKeyResourceBundleWrapperTest.java) | Adds prefix functionality by wrapping existing ResourceBundles.
[ParameterStoreResourceBundle](src/main/java/net/maxf/java/util/aws/ssm/ParameterStoreResourceBundle.java) | [ParameterStoreResourceBundleTest](src/test/java/net/maxf/java/util/aws/ssm/ParameterStoreResourceBundleTest.java) | Retrieves resources from AWS SSM Parameter Store. Useful to keep configuration in the cloud. 
[VaultResourceBundle](src/main/java/net/maxf/java/util/hashicorp/vault/VaultResourceBundle.java) | [VaultResourceBundleTest](src/test/java/net/maxf/java/util/hashicorp/vault/VaultResourceBundleTest.java) | Retrieves resources from HashiCorp Vault. Useful to keep secrets in the cloud. 
[CachingResourceBundle](src/main/java/net/maxf/java/util/CachingResourceBundle.java) | [CachingResourceBundleTest](src/test/java/net/maxf/java/util/CachingResourceBundleTest.java) | Caches values for ttl in milliseconds. Useful to avoid the expensive IO, and reuse last known values when backing bundle is not accessible.
[ResourceBundleChain](src/main/java/net/maxf/java/util/ResourceBundleChain.java) | [ResourceBundleChainTest](src/test/java/net/maxf/java/util/ResourceBundleChainTest.java) | Searches for configuration in a chain of bundles. Useful for overriding. Typical chain usually goes from most flexible to least flexible. e.g.: Cache -> Parameter Store -> Secrets Store -> Environment -> File. 


