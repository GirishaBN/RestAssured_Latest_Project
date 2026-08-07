package framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	private static ExtentReports extent;

	private ExtentReportManager() {
	}

	public static synchronized ExtentReports getInstance() {
		if(extent==null)
		{
		ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
		spark.config().setDocumentTitle("API Document Title");
		spark.config().setReportName("Rest Assured Automation");
		spark.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("RestAssured", "");
		extent.setSystemInfo("Environment",System.getProperty("env","qa")
        );
        extent.setSystemInfo("Java",System.getProperty("java.version"));
		}
		return extent;
		
	}

	public static synchronized void flush() {
		if(extent!=null)
		extent.flush();
	}
}
