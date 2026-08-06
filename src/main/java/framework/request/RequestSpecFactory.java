package framework.request;

import framework.auth.EnvironmentSecretProvider;
import framework.auth.SecretProvider;
import framework.auth.TokenManager;
import framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
	private RequestSpecFactory() {
	}
	private final static SecretProvider SECRET_PROVIDER = new EnvironmentSecretProvider();
   //private final static TokenManager TOKENMANAGER=new TokenManager(SECRET_PROVIDER );
	private static final String TOKEN=SECRET_PROVIDER.getSecret("BEARER_TOKEN");
    public static RequestSpecification defaultSpec() {
    	System.out.println("Bearer token loaded: " + (TOKEN != null && !TOKEN.isBlank()));
    	return new RequestSpecBuilder().setBaseUri(ConfigManager.get("api.base.url"))
				.setBasePath(ConfigManager.get("api.base.path")).setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer "+TOKEN).build();
	}
}
