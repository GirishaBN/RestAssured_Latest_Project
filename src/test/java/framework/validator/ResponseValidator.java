package framework.validator;

import org.testng.Assert;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class ResponseValidator {
public static void statusCode(Response response,int expectedStatusCode)
{
	int actualStatusCode=response.statusCode();
	Assert.assertEquals(actualStatusCode, expectedStatusCode,"Unexpected HTTP Status Code");
}
public static void contentType(Response response)
{
	String actualContentType=response.contentType();
	Assert.assertEquals(actualContentType, ContentType.JSON,"Unexpected Content Type");
}

}
