package framework.request;

import org.slf4j.Logger;

import framework.auth.EnvironmentSecretProvider;
import framework.auth.SecretProvider;
import framework.auth.TokenManager;
import framework.config.ConfigManager;
import framework.logging.ApiLoggingFilter;
import framework.logging.FrameworkLogger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
	private RequestSpecFactory() {
	}

	private static final Logger log = FrameworkLogger.getLogger(RequestSpecFactory.class);
	private final static SecretProvider SECRET_PROVIDER = new EnvironmentSecretProvider();
	// private final static TokenManager TOKENMANAGER=new
	// TokenManager(SECRET_PROVIDER );
	private static final String TOKEN = SECRET_PROVIDER.getSecret("BEARER_TOKEN");

	public static RequestSpecification defaultSpec() {
		boolean tokenLoaded = (TOKEN != null && !TOKEN.isBlank());
		log.debug("Creating default API RequestSpecification. tokenLoaded={}", tokenLoaded);
		return new RequestSpecBuilder().setBaseUri(ConfigManager.getString("api.base.url"))
				.setBasePath(ConfigManager.getString("api.base.path")).setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer " + TOKEN).addFilter(new ApiLoggingFilter()).build();
	}
}
