package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.auth.TokenManager;
import framework.model.request.UserRequest;
import framework.service.UserService;
import framework.validator.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserTest extends BaseTest {
	private UserService userservice;
	@BeforeClass(alwaysRun = true,description = "Verify user can be retrieved by ID")
	public void setupUserService()
	{
		userservice= new UserService(requestSpec);
	}
	private static int userId;
	private static String message;
	@Test(groups= {"smoke","regression"},priority=1)
	public void createUser_shouldReturnCreatedUser()
	{
		UserRequest request=new UserRequest("ram4","ram@gmail4.com","male","active");
		Response response=userservice.createUser(request);
		Assert.assertEquals(response.statusCode(), 201);
		JsonSchemaValidator.validate(response,"user-response.json");
		userId=response.jsonPath().getInt("id");
		Assert.assertNotNull(userId, "User ID should be generated");
		System.out.println("Created User ID: " + userId);
	}
	
	@Test(groups= {"smoke","regression"},priority=1)
	public void createUser_without_value_shouldReturnBadRequest()
	{
		UserRequest request=new UserRequest(" ","ram@gmail6.com","male","active");
		Response response=userservice.createUser(request);
		Assert.assertEquals(response.statusCode(), 422);
		JsonSchemaValidator.validate(response,"error-response.json");
		message=response.jsonPath().getString("message");
		Assert.assertNotNull(message, "can't be blank");
		System.out.println("error message is : " + message);
	}
	
	@Test(groups="smoke",priority=2)
	public void getUser_shouldReturnSucess() {
		Response response = userservice.getUser(userId);
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(groups= {"smoke","regression"},priority=3)
	public void deleteUser_shouldReturnNoContentType()
	{
		
		Response response=userservice.deleteUser(userId);
		Assert.assertEquals(response.statusCode(), 204);
		
	}
}
