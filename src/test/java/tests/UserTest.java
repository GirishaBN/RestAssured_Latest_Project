package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.auth.TokenManager;
import framework.model.request.UserRequest;
import framework.service.UserService;
import io.restassured.response.Response;

public class UserTest extends BaseTest {
	private UserService userservice;
	@BeforeClass(alwaysRun = true,description = "Verify user can be retrieved by ID")
	public void setupUserService()
	{
		userservice= new UserService(requestSpec, responseSpec);
	}
	int userId;
	@Test(groups= {"smoke","regression"},priority=1)
	public void createUser_shouldReturnCreatedUser()
	{
		UserRequest request=new UserRequest("ram2","ram@gmail2.com","male","active");
		Response response=userservice.createUser(request);
		Assert.assertEquals(response.statusCode(), 201);
		userId=response.jsonPath().getInt("id");
		
	}
	@Test(groups="smoke",priority=2)
	public void getUser_shouldReturnSucess() {
		Response response = userservice.getUsers();
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(groups= {"smoke","regression"},priority=3)
	public void deleteUser_shouldReturnCreatedUser()
	{
		
		Response response=userservice.deleteUser(userId);
		Assert.assertEquals(response.statusCode(), 204);
		
	}
}
