package framework.service;

import static io.restassured.RestAssured.given;

import framework.model.request.UserRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserService {
	private final RequestSpecification requestSpec;
	private final ResponseSpecification responseSpec;

	public UserService(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
		this.requestSpec = requestSpec;
		this.responseSpec = responseSpec;
	}

	public Response createUser(UserRequest request) {
		return given().spec(requestSpec).body(request).when().post("/users").then().extract().response();

	}

	public Response getUsers() {
		return given().spec(requestSpec).when().get("/users").then().spec(responseSpec).extract().response();
	}

	public Response getUser(int userID) {
		return given().spec(requestSpec).when().get("/users/{id}", userID).then().spec(responseSpec).extract()
				.response();
	}

}
