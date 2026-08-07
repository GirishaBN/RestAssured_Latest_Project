package framework.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import framework.reporting.ExtentReportManager;
import framework.reporting.ExtentTestManager;

public class ExtentTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		String className =result.getTestClass().getName();
		String methodName =result.getMethod().getMethodName();
		String groups =String.join(",",result.getMethod().getGroups());
		
		ExtentTest test =ExtentReportManager.getInstance().createTest(methodName);
		test.assignCategory(groups);
		test.info("Class: " + className);
		test.info("Groups: " + groups);
		ExtentTestManager.setTest(test);
		test.log(Status.INFO, "Test execution started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().pass("Test passed successfully");
		ExtentTestManager.removeTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTestManager.getTest().fail(result.getThrowable());
		ExtentTestManager.removeTest();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().skip("Test skipped");
		ExtentTestManager.removeTest();
	}
	
	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.flush();
	}

}
