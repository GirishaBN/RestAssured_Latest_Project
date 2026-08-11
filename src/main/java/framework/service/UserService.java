package framework.service;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;

import framework.logging.FrameworkLogger;
import framework.model.request.UserRequest;
import framework.request.ResponseSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserService {
	private static final Logger log=FrameworkLogger.getLogger(UserService.class);
	
	private final RequestSpecification requestSpec;

	public UserService(RequestSpecification requestSpec) {
		this.requestSpec = requestSpec;
		log.debug("UserService initialized");
		}

	public Response createUser(UserRequest request) {
		log.info("Executing CREATE user API");
		Response response = given().spec(requestSpec).body(request).when().post("/users").then().spec(ResponseSpecFactory.createResponseSpec()).extract().response();
		 log.info("CREATE user API completed. statusCode={}",
	                response.statusCode());
		 return response;
	}

	public Response getUsers() {
		return given().spec(requestSpec).when().get("/users").then().spec(ResponseSpecFactory.getResponseSpec()).extract().response();
	}

	public Response getUser(int userId) {
		log.info("Executing GET user API. userId={}",userId);
		/*
		 * return given().spec(requestSpec).when().get("/users/{id}",
		 * userId).then().spec(ResponseSpecFactory.getResponseSpec()).extract()
		 * .response();
		 */
		 return given().spec(requestSpec).get("https://httpbin.org/status/429");
	}
	
	public Response deleteUser(int userID) {
		return given().spec(requestSpec).when().delete("/users/{id}",userID).then().spec(ResponseSpecFactory.deleteResponseSpec()).extract().response();

	}
	
	public Response createNegativeUser(UserRequest request)
	{
		return given().spec(requestSpec).body(request).when().post("/users").then().spec(ResponseSpecFactory.validateErrorResponseSpec()).extract().response();
		
		
	}

}
