package Base;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners extends BaseClass implements ITestListener {

	public void onTestStart(ITestResult result) {
		System.out.println(result.getName() + " started executing");
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println(result.getName() + " executed successfully");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println(result.getName() + " got failed");
		System.out.println("Capturing ScreenShot for "+result.getName());
		failed(result.getName());
	}

}
