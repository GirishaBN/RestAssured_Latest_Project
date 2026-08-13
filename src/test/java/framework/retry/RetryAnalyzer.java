package framework.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import framework.config.ConfigManager;

public class RetryAnalyzer implements IRetryAnalyzer {

	private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

	private static final int MAX_RETRY = ConfigManager.getInt("retry.count");

	private int retryCount = 0;

	@Override
	public boolean retry(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		
		if (!ConfigManager.getBoolean("retry.enabled")) {
	        return false;
	    }
		
		if (retryCount >= MAX_RETRY) {

			log.warn("Maximum retry attempts reached. Test={}", methodName);
			RetryContext.clear();
			return false;
		}

		Integer statusCode = RetryContext.getStatusCode();

		Throwable throwable = result.getThrowable();

		boolean retryableStatus = RetryPolicy.isRetryableStatus(statusCode);

		boolean retryableException = RetryPolicy.isRetryableException(throwable);

		if (retryableStatus || retryableException) {

			retryCount++;

			log.warn("Retrying test. test={}, statusCode={}, retryAttempt={}/{}", methodName, statusCode, retryCount,
					MAX_RETRY);

			return true;
		}

		log.info("Test failure is not retryable. test={}, statusCode={}", methodName, statusCode);

		RetryContext.clear();

		return false;
	}
}