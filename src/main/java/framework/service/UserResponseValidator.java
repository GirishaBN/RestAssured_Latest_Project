package framework.service;

import framework.model.response.UserResponse;
import io.restassured.response.Response;

public class UserResponseValidator {
public UserResponse validator(Response response)
{
	return response.as(UserResponse.class);
}
}
