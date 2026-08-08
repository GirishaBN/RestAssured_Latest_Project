package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.service.UserService;
import io.restassured.response.Response;

public class UsersGetTest extends BaseTest {
	private UserService userservice;
	
	@BeforeMethod(alwaysRun = true,description = "Verify user can be retrieved by ID")
	public void setupUserService()
	{
		userservice= new UserService(requestSpec);
	}
	
	@Test(groups="sanity")
	public void getSingleUser_shouldReturnSucess() {
		int userID=8572725;
		Response response = userservice.getUser(userID);
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(groups="sanity")
	public void getAllUsers() {
	    Response response = userservice.getUsers();
	    Assert.assertEquals(response.statusCode(), 200);
	}

}
