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
	@Test(groups= {"smoke","regression"},priority=1)
	public void createUser_shouldReturnCreatedUser()
	{
		UserRequest request=new UserRequest("ram5","ram@gmail5.com","male","active");
		Response response=userservice.createUser(request);
		Assert.assertEquals(response.statusCode(), 400,"Intentional failure to verify Extent Report");
		JsonSchemaValidator.validate(response,"user-response.json");
		userId=response.jsonPath().getInt("id");
		Assert.assertNotNull(userId, "User ID should be generated");
		System.out.println("Created User ID: " + userId);
	}
	
	@Test(groups= {"smoke","regression"},priority=2)
	public void createUser_without_value_shouldReturnBadRequest()
	{
		UserRequest request=new UserRequest(" ","ram@gmail5.com","male","active");
		Response response=userservice.createNegativeUser(request);
		JsonSchemaValidator.validate(response,"error-response.json");
		String nameMessage = response.jsonPath().getString("find { it.field == 'name' }.message");
		String emailMessage = response.jsonPath().getString("find { it.field == 'email' }.message");
		Assert.assertEquals(nameMessage, "can't be blank");
		Assert.assertEquals(emailMessage, "has already been taken");
	}
	
	@Test(groups="smoke",priority=3,dependsOnMethods = "createUser_shouldReturnCreatedUser")
	public void getUser_shouldReturnSucess() {
		Response response = userservice.getUser(userId);
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(groups= {"smoke","regression"},priority=4,dependsOnMethods = "createUser_shouldReturnCreatedUser")
	public void deleteUser_shouldReturnNoContentType()
	{
		Response response=userservice.deleteUser(userId);
		Assert.assertEquals(response.statusCode(), 204);
	}
}
