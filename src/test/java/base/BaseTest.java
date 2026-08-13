package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import framework.request.RequestSpecFactory;
import framework.request.RequestSpecManager;

public class BaseTest {
	@BeforeMethod(alwaysRun = true)
	public void setup() {
		RequestSpecManager.set(RequestSpecFactory.defaultSpec());
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		RequestSpecManager.clear();
	}
}
