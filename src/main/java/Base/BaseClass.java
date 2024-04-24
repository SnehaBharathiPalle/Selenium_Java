package Base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseClass {
	public static WebDriver driver;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	String dest = "./ExtentReport/report.html";
	ArrayList<String> methodList = new ArrayList<>();

	@BeforeTest
	public void launchBrowser() {

		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@BeforeMethod
	public void launchApplication() {

		driver.get("https://www.selenium.dev/");
	}

	@AfterMethod
	public void checkStatus(ITestResult result) {
		String methodName = result.getName();

		if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest = extentReports.createTest(methodName);
			extentTest.log(Status.PASS, methodName + " is a passed test case");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			extentTest = extentReports.createTest(methodName);
			extentTest.log(Status.FAIL, methodName + " is a failed test case")
					.addScreenCaptureFromPath(failed(methodName));
		}
	}

	@AfterTest
	public void close() {
		driver.quit();
	}

	public String failed(String methodName) {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String des = "./Screenshots/" + methodName + ".png";
		File destFile = new File(des);
		try {
			FileUtils.copyFile(source, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destFile.getAbsolutePath();

	}

	@BeforeSuite
	public void intializeExtentReport() {

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(new File(dest));
		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);

	}

	@AfterSuite
	public void generateReport() throws IOException {
		extentReports.flush();
		// Desktop.getDesktop().browse(new File(dest).toURI());
	}

}
