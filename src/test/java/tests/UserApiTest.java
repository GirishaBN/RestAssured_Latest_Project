package tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.model.request.UserRequest;
import framework.request.ResponseSpecFactory;
import framework.service.UserService;
import io.restassured.response.Response;

public class UserApiTest extends BaseTest {

	private UserService userService;
	int userId;
	@BeforeClass(alwaysRun = true)
	public void setupUserService() {
		userService = new UserService();
	}

	// =========================
	// CREATE
	// =========================

	@Test(groups = { "smoke", "regression" })
	public void createUser_shouldReturnCreated() {

		UserRequest request = new UserRequest("ram"+UUID.randomUUID(), "ram"+UUID.randomUUID()+"@gmail.com", "male", "active");

		Response response = userService.createUser(request);
		response.then().spec(ResponseSpecFactory.createResponseSpec());
		userId = response.jsonPath().getInt("id");

		Assert.assertTrue(userId > 0, "User ID should be generated");
	}

	// =========================
	// GET ALL
	// =========================

	@Test(groups = { "smoke", "regression" })
	public void getAllUsers_shouldReturnSuccess() {

		Response response = userService.getUsers();
		response.then().spec(ResponseSpecFactory.getResponseSpec());
		Assert.assertEquals(response.statusCode(), 200);
	}

	// =========================
	// GET SINGLE
	// =========================

	@Test(groups = { "smoke", "regression" })
	public void getSingleUser_shouldReturnSuccess() {

		Response response = userService.getUser(userId);
		response.then().spec(ResponseSpecFactory.getResponseSpec());
		Assert.assertEquals(response.statusCode(), 200);
	}

	// =========================
	// UNKNOWN USER
	// =========================

	@Test(groups = { "negative", "regression" })
	public void getUnknownUser_shouldReturnNotFound() {

		int userId = 999999999;

		Response response = userService.getUser(userId);
		response.then().spec(ResponseSpecFactory.validateErrorResponseSpec());

		Assert.assertEquals(response.statusCode(), 404);

		String message = response.jsonPath().getString("message");

		Assert.assertEquals(message, "Resource not found");
	}

	// =========================
	// CRUD WORKFLOW
	// =========================

	@Test(groups = { "regression" })
	public void createGetDeleteUser_shouldCompleteSuccessfully() {

		UserRequest request = new UserRequest("parallelUser", "parallelUser@gmail.com", "male", "active");

		// CREATE
		Response createResponse = userService.createUser(request);

		Assert.assertEquals(createResponse.statusCode(), 201);

		int userId = createResponse.jsonPath().getInt("id");

		Assert.assertTrue(userId > 0, "Created user ID should be greater than zero");

		// GET
		Response getResponse = userService.getUser(userId);

		Assert.assertEquals(getResponse.statusCode(), 200);

		// DELETE
		Response deleteResponse = userService.deleteUser(userId);

		Assert.assertEquals(deleteResponse.statusCode(), 204);
	}
}