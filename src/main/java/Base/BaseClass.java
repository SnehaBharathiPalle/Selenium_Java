package Base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseClass {
	public static WebDriver driver;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String browser;
	String dest = "./ExtentReport/report.html";
	String hubURL = "http://localhost:4444";

	@BeforeTest
	@Parameters({ "browserName", "runMode" })
	public void launchBrowser(@Optional("chrome") String browserName, @Optional("local") String runMode)
			throws MalformedURLException {
		browser = browserName;
		DesiredCapabilities dc = new DesiredCapabilities();
		if (browserName.equals("chrome")) {
			if (runMode.equals("local")) {
				driver = new ChromeDriver();
				System.out.println("CHROME DRIVER");
			} else {
				dc.setBrowserName(browserName);
				driver = new RemoteWebDriver(new URL(hubURL), dc);
				System.out.println("CHROME DRIVER");
			}
		} else {
			if (runMode.equals("local")) {
				driver = new EdgeDriver();
				System.out.println("EDGE DRIVER");
			} else {
				browserName = "MicrosoftEdge";
				dc.setBrowserName(browserName);
				driver = new RemoteWebDriver(new URL(hubURL), dc);
				System.out.println("EDGE DRIVER");
			}
			driver.manage().window().maximize();

		}
	}

	@BeforeMethod
	public void launchApplication() {
		driver.get("https://www.selenium.dev/");
	}

	@AfterMethod
	public void checkStatus(ITestResult result) {
		System.out.println("******************************");
		String methodName = result.getName();

		if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest = extentReports.createTest(methodName).log(Status.INFO, methodName + " ran on " + browser)
					.log(Status.PASS, methodName + " is a passed test case");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			extentTest = extentReports.createTest(methodName);
			extentTest.log(Status.FAIL, methodName + " is a failed test case")
			.log(Status.INFO, methodName + " ran on " + browser)
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
