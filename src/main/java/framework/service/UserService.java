package framework.service;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;

import framework.logging.FrameworkLogger;
import framework.model.request.UserRequest;
import framework.request.RequestSpecManager;
import io.restassured.response.Response;

public class UserService {

	private static final Logger log = FrameworkLogger.getLogger(UserService.class);

	public Response createUser(UserRequest request) {

		log.info("Executing CREATE user API");

		return given().spec(RequestSpecManager.get()).body(request).when().post("/users").then().extract().response();
	}

	public Response getUsers() {

		log.info("Executing GET all users API");

		return given().spec(RequestSpecManager.get()).when().get("/users").then().extract().response();
	}

	public Response getUser(int userId) {

		log.info("Executing GET user API. userId={}", userId);

		return given().spec(RequestSpecManager.get()).when().get("/users/{id}", userId).then().extract().response();
	}

	public Response deleteUser(int userId) {

		log.info("Executing DELETE user API. userId={}", userId);

		return given().spec(RequestSpecManager.get()).when().delete("/users/{id}", userId).then().extract().response();
	}
}