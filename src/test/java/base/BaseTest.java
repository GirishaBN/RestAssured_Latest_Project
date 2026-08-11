package base;

import org.testng.annotations.BeforeClass;

import framework.request.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest {
protected RequestSpecification requestSpec;
protected ResponseSpecification responseSpec;
@BeforeClass(alwaysRun = true)
public void setup()
{
	requestSpec=RequestSpecFactory.defaultSpec();
	System.out.println("requestSpec = " + requestSpec);
}
}
