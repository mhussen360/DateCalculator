package com.timeanddate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class DateCalculatorTest {

	@Test
	public void subtractDates() {

		// Steps to test calculate function
		System.out.println("Starting searchFlightTest");

		// Step 1: Create Driver (What is a driver)
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize(); // Full screen

		// Step 2: Open Page
		String url = "https://www.timeanddate.com/date/dateadd.html";
		driver.get(url);
		System.out.println("Page is opened succefully");

		// Enter Start Dates (mm/dd/yyyy)
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='m1']")).sendKeys("03");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='d1']")).sendKeys("22");
		driver.findElement(By.xpath("/html//form[@id='f']/div[1]//input[@name='y1']")).sendKeys("2019");

		// select subtract from drop down menu
		WebElement addSubMenu = driver.findElement(By.xpath("/html//select[@id='type']"));
		Select subtract = new Select(addSubMenu);
		subtract.selectByValue("sub");

		// Enter number of years, months, days, ad week to sub from
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
		System.out.println(newDate);

	}
}
