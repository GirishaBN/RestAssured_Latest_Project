package framework.request;

import framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
	private RequestSpecFactory() {
	}

	public static RequestSpecification defaultSpec() {
		return new RequestSpecBuilder().setBaseUri(ConfigManager.get("api.base.url"))
				.setBasePath(ConfigManager.get("api.base.path")).setContentType(ContentType.JSON).build();
	}
}
