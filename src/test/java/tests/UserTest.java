package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.service.UserService;
import io.restassured.response.Response;

public class UserTest extends BaseTest {
	private UserService userservice;
	@BeforeClass(alwaysRun = true,description = "Verify user can be retrieved by ID")
	public void setupUserService()
	{
		this.userservice= new UserService(requestSpec, responseSpec);
	}

	@Test(groups="smoke")
	public void getUser_shouldReturnSucess() {
		Response response = userservice.getUsers();
		Assert.assertEquals(response.statusCode(), 200);
	}
}
