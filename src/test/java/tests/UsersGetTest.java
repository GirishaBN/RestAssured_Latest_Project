package tests;


import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import framework.logging.FrameworkLogger;
import framework.service.UserService;
import io.restassured.response.Response;

public class UsersGetTest extends BaseTest {
	private UserService userservice;
	private static final Logger logger=FrameworkLogger.getLogger(UsersGetTest.class);
	
	@BeforeMethod(alwaysRun = true,description = "Verify user can be retrieved by ID")
	public void setupUserService()
	{
		userservice= new UserService(requestSpec);
	}
	
	@Test(groups="sanity")
	public void getSingleUser_shouldReturnSucess() {
		logger.info("Started getSingleUser| Thread="+Thread.currentThread().getName()+"| Time: "+System.currentTimeMillis());
		int userID=8572725;
		Response response = userservice.getUser(userID);
		Assert.assertEquals(response.statusCode(), 200);
		logger.info("Ended getSingleUser| Thread="+Thread.currentThread().getName()+"| Time: "+System.currentTimeMillis());
		
	}
	
	@Test(groups="sanity")
	public void getAllUsers() {
		logger.info("Started getAllUsers| Thread="+Thread.currentThread().getName()+"| Time: "+System.currentTimeMillis());
	    Response response = userservice.getUsers();
	    Assert.assertEquals(response.statusCode(), 200);
	    logger.info("Ended getAllUsers| Thread="+Thread.currentThread().getName()+"| Time: "+System.currentTimeMillis());
	}

}
