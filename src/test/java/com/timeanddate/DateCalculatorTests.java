package com.timeanddate;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DateCalculatorTests { // Testing a calculator that adds or subtracts two dates

	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {

		// Steps to test calculate function
		System.out.println("Starting DateCalcualtorTest");

		// Step 1: Create Driver (What is a driver)
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize(); // Full screen

		// Step 2: Open Page
		String url = "https://www.timeanddate.com/date/dateadd.html";
		driver.get(url);
		System.out.println("Page is opened succefully");

	}

	@Test(priority = 1, groups = { "positiveTest" })
	public void subtractDates() {

		// Enter Start Dates (mm/dd/yyyy)
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='m1']")).sendKeys("03");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='d1']")).sendKeys("22");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='y1']")).sendKeys("2019");

		// select subtract from drop down menu
		WebElement addSubMenu = driver.findElement(By.xpath("/html//select[@id='type']"));
		Select subtract = new Select(addSubMenu);
		subtract.selectByValue("sub");

		// Enter number of years, months, days, and weeks to subtract from
		driver.findElement(By.xpath("/html//input[@id='ay']")).sendKeys("30");
		driver.findElement(By.xpath("/html//input[@id='am']")).sendKeys("4");
		driver.findElement(By.xpath("/html//input[@id='aw']")).sendKeys("53");
		driver.findElement(By.xpath("/html//input[@id='ad']")).sendKeys("200");

		// Calculate New Date
		WebElement el = driver.findElement(By.xpath("/html//input[@id='subbut2']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);
		String newDate = driver.findElement(By.xpath(
				"/html/body/div[@class='main-content-div']/div[@class='fixed']//h2[.='Result: Tuesday, April 28, 1987']"))
				.getText();

		//Verify that actual date matches the expected calculated date. 
		Assert.assertTrue(newDate.contains("Result: Tuesday, April 28, 1987"),
				"Actual Date does not match expected Date");

	}

	@Parameters({ "year" })
	@Test(priority = 2, groups = { "largeYearTest" })
	public void largeyearTest(String year) {

		// Enter Start Dates (mm/dd/yyyy)
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='m1']")).sendKeys("03");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='d1']")).sendKeys("22");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='y1']")).sendKeys("2019");

		// select subtract from drop down menu
		WebElement addSubMenu = driver.findElement(By.xpath("/html//select[@id='type']"));
		Select subtract = new Select(addSubMenu);
		subtract.selectByValue("sub");

		// Enter number of years, months, days, and weeks to subtract from
		driver.findElement(By.xpath("/html//input[@id='ay']")).sendKeys(year);
		driver.findElement(By.xpath("/html//input[@id='am']")).sendKeys("4");
		driver.findElement(By.xpath("/html//input[@id='aw']")).sendKeys("53");
		driver.findElement(By.xpath("/html//input[@id='ad']")).sendKeys("200");

		// Calculate New Date
		WebElement el = driver.findElement(By.xpath("/html//input[@id='subbut2']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);
		
		//Verify that an error message appears when a large year is entered
		String ActualErrorMessage = driver.findElement(By.xpath("//div[@id='closeme']/p")).getText();
		Assert.assertTrue(ActualErrorMessage.contains(
				"The resulting date is before year 1. This calculator only supports dates between year 1 and 3999."),
				"Actual Message: " + ActualErrorMessage);

	}


	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser - Destroys driver instance
		driver.quit();
	}

}
