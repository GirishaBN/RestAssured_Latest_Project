package framework.request;

import static org.hamcrest.Matchers.lessThan;

import framework.config.ConfigManager;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecFactory {
	private ResponseSpecFactory() {
	}

	private static final long RESPONSE_TIMEOUT = ConfigManager.getLong("api.response.timeout.ms");

	public static ResponseSpecification getResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(RESPONSE_TIMEOUT)).build();
	}

	public static ResponseSpecification createResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(RESPONSE_TIMEOUT)).build();
	}

	public static ResponseSpecification updateResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.expectResponseTime(lessThan(RESPONSE_TIMEOUT)).build();
	}

	public static ResponseSpecification deleteResponseSpec() {
		return new ResponseSpecBuilder().expectStatusCode(204).expectResponseTime(lessThan(RESPONSE_TIMEOUT)).build();
	}
	public static ResponseSpecification validateErrorResponseSpec()
	{
		return new ResponseSpecBuilder().expectStatusCode(422).expectContentType(ContentType.JSON).build();
	}
}
