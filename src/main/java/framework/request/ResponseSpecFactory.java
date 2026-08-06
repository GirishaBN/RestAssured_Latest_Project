package framework.request;

import static org.hamcrest.Matchers.lessThan;

import framework.config.ConfigManager;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecFactory {
	private ResponseSpecFactory() {
	}

	public static ResponseSpecification defaultSpec() {
		return new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(ConfigManager.getLong("api.response.timeout.ms"))).build();
	}
}
