package framework.validator;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;;

public final class JsonSchemaValidator {
	public static void validate(Response response, String schemaFile) {
		response.then().assertThat().body( matchesJsonSchemaInClasspath("schemas/"+schemaFile));
	}
}
