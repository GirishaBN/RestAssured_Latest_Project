package framework.service;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;

import framework.logging.FrameworkLogger;
import framework.model.request.UserRequest;
import framework.request.RequestSpecManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserService {

	private static final Logger log = FrameworkLogger.getLogger(UserService.class);
	RequestSpecification request = given().spec(RequestSpecManager.get());

	public Response createUser(UserRequest request) {

		log.info("Executing CREATE user API");

		Response response = given().spec(RequestSpecManager.get()).body(request).when().post("/users").then().extract()
				.response();
		log.info("CREATE user API completed. status={}", response.statusCode());
		return response;

	}

	public Response getUsers() {

		log.info("Executing GET all users API");

		Response response = given().spec(RequestSpecManager.get()).when().get("/users").then().extract().response();
		log.info("GET all users API completed. status={}", response.statusCode());
		return response;
	}

	public Response getUser(int userId) {

		log.info("Executing GET user API. userId={}", userId);

		Response response = given().spec(RequestSpecManager.get()).when().get("/users/{id}", userId).then().extract()
				.response();
		log.info("GET user API completed. userId={}, status={}", userId, response.statusCode());

		return response;
	}

	public Response deleteUser(int userId) {

		log.info("Executing DELETE user API. userId={}", userId);

		Response response = given().spec(RequestSpecManager.get()).when().delete("/users/{id}", userId).then().extract()
				.response();
		log.info("DELETE user API completed. userId={}, status={}", userId, response.statusCode());
		return response;
	}

}