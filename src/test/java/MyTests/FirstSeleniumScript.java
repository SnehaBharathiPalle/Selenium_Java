package MyTests;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;

public class FirstSeleniumScript extends BaseClass {
	@Test
	public void demo0() {
		System.out.println("The thread ID  is " + Thread.currentThread().getId());
		String expectedTitle = driver.getTitle();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals("Seleniu", expectedTitle);
		softAssert.assertEquals("Selenium", expectedTitle);
		softAssert.assertAll();
	}

	@Test(dataProvider = "myData")
	public void dataProviderTest(String name, String place) {
		System.out.println("The thread ID for Chrome is " + Thread.currentThread().getId());
		System.out.println(name + " is form " + place);
	}

	@DataProvider  //(parallel = true)
	public Object[][] myData() {
		String[][] data = new String[][] { { "Sne", "ATP" }, { "Sreedhar", "KNL" }, { "Bharath", "ATP" } };
		return data;
	}

	@Test(groups= {"complexy"})
	public void demo1() {
		System.out.println("The thread ID for Chrome is " + Thread.currentThread().getId());
		By aboutLink= By.xpath("//a[text()='About']");
		driver.findElement(aboutLink).click();
		By by = By.xpath("//a[text()='About']//following-sibling::div//a");
		List<WebElement> ele = driver.findElements(by);
		// Get the window handle of the parent window
		String parentWindowHandle = driver.getWindowHandle();
		for (WebElement w : ele) {
			String sublink = w.getAttribute("href");
			System.out.println(sublink);
			System.out.println(w.getAttribute("innerText"));
			// Click on a link that opens a new window
			//w.click();
			driver.switchTo().newWindow(WindowType.WINDOW);
			driver.get(sublink);
			String childWindow = driver.getWindowHandle();
			driver.switchTo().window(parentWindowHandle);
			driver.switchTo().window(childWindow);;
			driver.close();
			driver.switchTo().window(parentWindowHandle);
			
//			// Get the window handles of all the windows that are currently open
//			Set<String> allWindowHandles = driver.getWindowHandles();
//			// Loop through the window handles to find the handle of the new window
//			for (String windowHandle : allWindowHandles) {
//				if (!windowHandle.equals(parentWindowHandle)) {
//					driver.switchTo().window(windowHandle);
//					break;
//				}
//			}

		}
		String expected = String.valueOf(ele.size());
		Assert.assertEquals("2", expected);
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
