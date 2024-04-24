package MyTests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;

public class FirstSeleniumScript extends BaseClass {
	@Test // (retryAnalyzer = Base.MyRetry.class)
	public void demo0() {
		String expectedTitle = driver.getTitle();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("Seleniu", expectedTitle);
		softAssert.assertAll();
	}

	@Test(dataProvider = "myData")
	public void dataProviderTest(String name, String place) {
		System.out.println(name + " is form " + place);
	}

	@DataProvider
	public Object[] myData() {
		String[][] data = new String[][] { { "Sne", "ATP" }, { "Sreedhar", "KNL" }, { "Bharath", "ATP" } };
		return data;
	}

	@Ignore
	@Test
	public void demo1() {
		System.out.println("The thread ID for Chrome is " + Thread.currentThread().getId());
		By by = By.xpath("//a[text()='About']//following-sibling::div//a");
		List<WebElement> ele = driver.findElements(by);
		for (WebElement w : ele) {
			System.out.println(w.getAttribute("href"));
			System.out.println(w.getAttribute("innerText"));
			String expected = String.valueOf(ele.size());
			Assert.assertEquals("2", expected);
		}

	}

	@Test
	public void aha() {
		System.out.println("The thread ID for Chrome is " + Thread.currentThread().getId());
		By by = By.xpath("//div[@id='main_navbar']//li//a");
		List<WebElement> ele = driver.findElements(by);
		for (WebElement w : ele) {
			System.out.println(w.getAttribute("innerText"));
		}
	}

}
