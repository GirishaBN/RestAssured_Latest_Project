package framework.service;

import static io.restassured.RestAssured.given;

import framework.model.request.UserRequest;
import framework.request.ResponseSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserService {
	private final RequestSpecification requestSpec;

	public UserService(RequestSpecification requestSpec) {
		this.requestSpec = requestSpec;
		}

	public Response createUser(UserRequest request) {
		return given().spec(requestSpec).body(request).when().post("/users").then().spec(ResponseSpecFactory.createResponseSpec()).extract().response();

	}

	public Response getUsers() {
		return given().spec(requestSpec).when().get("/users").then().spec(ResponseSpecFactory.getResponseSpec()).extract().response();
	}

	public Response getUser(int userID) {
		return given().spec(requestSpec).when().get("/users/{id}", userID).then().spec(ResponseSpecFactory.getResponseSpec()).extract()
				.response();
	}
	
	public Response deleteUser(int userID) {
		return given().spec(requestSpec).when().delete("/users/{id}",userID).then().spec(ResponseSpecFactory.deleteResponseSpec()).extract().response();

	}

}
