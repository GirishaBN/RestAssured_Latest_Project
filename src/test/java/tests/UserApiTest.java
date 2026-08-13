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

    @BeforeClass(alwaysRun = true)
    public void setupUserService() {
        userService = new UserService();
    }

    @Test(groups = {"smoke", "regression"})
    public void createUser_shouldReturnCreated() {

        String uniqueId = UUID.randomUUID().toString();

        UserRequest request =
                new UserRequest(
                        "ram_" + uniqueId,
                        "ram_" + uniqueId + "@gmail.com",
                        "male",
                        "active"
                );

        Response response =
                userService.createUser(request);

        response.then()
                .spec(ResponseSpecFactory.createResponseSpec());

        int userId =
                response.jsonPath().getInt("id");

        Assert.assertTrue(
                userId > 0,
                "User ID should be generated"
        );
    }

    @Test(groups = {"smoke", "regression"})
    public void getAllUsers_shouldReturnSuccess() {

        Response response =
                userService.getUsers();

        response.then()
                .spec(ResponseSpecFactory.getResponseSpec());
    }

    @Test(groups = {"smoke", "regression"})
    public void getSingleUser_shouldReturnSuccess() {

        String uniqueId = UUID.randomUUID().toString();

        UserRequest request =
                new UserRequest(
                        "user_" + uniqueId,
                        "user_" + uniqueId + "@gmail.com",
                        "male",
                        "active"
                );

        Response createResponse =
                userService.createUser(request);

        createResponse.then()
                .spec(ResponseSpecFactory.createResponseSpec());

        int userId =
                createResponse.jsonPath().getInt("id");

        Assert.assertTrue(
                userId > 0,
                "User ID should be generated"
        );

        Response getResponse =
                userService.getUser(userId);

        getResponse.then()
                .spec(ResponseSpecFactory.getResponseSpec());
    }

    @Test(groups = {"negative", "regression"})
    public void getUnknownUser_shouldReturnNotFound() {

        int userId = 999999999;

        Response response =
                userService.getUser(userId);

        response.then()
                .spec(ResponseSpecFactory.validateErrorResponseSpec());

        String message =
                response.jsonPath().getString("message");

        Assert.assertEquals(
                message,
                "Resource not found"
        );
    }

    @Test(groups = {"regression"})
    public void createGetDeleteUser_shouldCompleteSuccessfully() {

        String uniqueId = UUID.randomUUID().toString();

        UserRequest request =
                new UserRequest(
                        "parallelUser_" + uniqueId,
                        "parallelUser_" + uniqueId + "@gmail.com",
                        "male",
                        "active"
                );

        // CREATE
        Response createResponse =
                userService.createUser(request);

        createResponse.then()
                .spec(ResponseSpecFactory.createResponseSpec());

        int userId =
                createResponse.jsonPath().getInt("id");

        Assert.assertTrue(
                userId > 0,
                "Created user ID should be greater than zero"
        );

        // GET
        Response getResponse =
                userService.getUser(userId);

        getResponse.then()
                .spec(ResponseSpecFactory.getResponseSpec());

        // DELETE
        Response deleteResponse =
                userService.deleteUser(userId);

        deleteResponse.then()
                .spec(ResponseSpecFactory.deleteResponseSpec());
    }
}