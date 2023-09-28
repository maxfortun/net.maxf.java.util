# net.maxf.java.util

Extending java.util classes for that extra convenience :)  

Class | Description 
--- | ---
[EnvResourceBundle](src/main/java/net/maxf/java/util/EnvResourceBundle.java) | Retrieves resources from the environment variables. Useful for runtimes that automatically provide configuration.
[MapResourceBundle](src/main/java/net/maxf/java/util/MapResourceBundle.java) | General purpose Map wrapper. Useful with any data provider that returns a map.
[PrefixedKeyResourceBundle](src/main/java/net/maxf/java/util/PrefixedKeyResourceBundle.java) | Allows to specify key prefix. Useful when working with large resource bundles, but only need a subset of keys.
[ParameterStoreResourceBundle](src/main/java/net/maxf/java/util/aws/ssm/ParameterStoreResourceBundle.java) | Retrieves resources from AWS SSM Parameter Store. Useful to keep configuration in the cloud. 
[ResourceBundleChain](src/main/java/net/maxf/java/util/ResourceBundleChain.java) | Searches for configuration in a chain of bundles. Useful for overriding. Typical chain usually goes from most flexible to least flexible. e.g.: SSM -> Env -> File. 

