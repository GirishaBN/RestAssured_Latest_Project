package framework.auth;

public class EnvironmentSecretProvider implements SecretProvider {

	@Override
	public String getSecret(String key) {
		
		 String value=System.getenv(key);
		 if(value==null||value.isBlank())
		 {
			 throw new IllegalStateException("Required secrete is missing: "+ key);
		 }
		 return value;
	}

}
