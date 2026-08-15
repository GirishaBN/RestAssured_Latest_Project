package framework.testdata;

import java.util.UUID;

import org.testng.annotations.DataProvider;

import framework.model.request.UserRequest;

public class UserDataProvider {
	
	
	@DataProvider(name = "userData")
	public Object[][] userData() {
		String uniqueId = UUID.randomUUID().toString();
		return new Object[][] {
				{ new UserRequest("ram_" + uniqueId, "ram_" + uniqueId + "@gmail.com", "male", "active") },
				{ new UserRequest("john" + uniqueId, "john" + uniqueId + "@gmail.com", "male", "active") },
				{ new UserRequest("sita" + uniqueId, "sita" + uniqueId + "@gmail.com", "female", "active") } };
	};
	
    public static final int UNKNOWN_USER_ID = 999999999;

}
