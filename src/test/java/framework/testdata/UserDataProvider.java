package framework.testdata;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.DataProvider;

import framework.model.request.UserRequest;

public class UserDataProvider {

	@DataProvider(name = "userData", parallel = true)
	public Object[][] userData() {
		String uniqueId = UUID.randomUUID().toString();
		return new Object[][] {
				{ new UserRequest("ram_" + uniqueId, "ram_" + uniqueId + "@gmail.com", "male", "active") },
				{ new UserRequest("john" + uniqueId, "john" + uniqueId + "@gmail.com", "male", "active") },
				{ new UserRequest("sita" + uniqueId, "sita" + uniqueId + "@gmail.com", "female", "active") } };
	};

	public static int unknownUserId() {
		return ThreadLocalRandom.current().nextInt(1_000_000_000, Integer.MAX_VALUE);
	}

}
