package com.dice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {
	public static void main(String[] args) {
		String count;
		
		List<String> s1 = new ArrayList();
		
//		s1.add("Phyton");
//		s1.add("JAVA");
//		s1.add("Selenium");
//		s1.add("QTP");
		
	
		
		System.out.println(s1.size());
		
		Faker c1 = new Faker();
		
		for(int i=0; i<5; i++) {
			s1.add(c1.company().name());
		}
		
		
		//		System.setProperty("webdriver.chrome.driver",
		//		"C:\\Users\\Yaroslav Kryvda\\Documents\\selenium dependencies\\drivers\\chromedriver.exe");
		
		//Set up chrome driver path
		WebDriverManager.chromedriver().setup();
		
		//Invoke selenium webdriver
		WebDriver driver = new ChromeDriver();
		
		//full Screen
		driver.manage().window().maximize(); //or maximize fullscreen();
		
		//set universal wait time in case web page is slow
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		/*Step 1. Launch browser and navigate to dice.com 
		Expected: dice home page should be displayed.
		*/
		
		String url = "https://www.dice.com/";
		//driver.navigate().to(url);
		driver.get(url);
		
		String actualTitle = driver.getTitle();
		String expectedTitle = "Job Search for Technology Professionals | Dice.com";
		
		if(actualTitle.equals(expectedTitle)) {
			System.out.println("Dice homePage succefully loaded");
		}else {
			System.out.println("Step FAIL. Did NOT load succefully");
			throw new RuntimeException("Step FAIL. Did NOT load succefully");
		}
		
		
		/*
		 * Step 2. Insert search keyword amd location then click on find tech jobs
		 */
		for(int i=0; i<s1.size(); i++) {
			
			
		String keyword= s1.get(i);
		driver.findElement(By.id("search-field-keyword")).clear();
		driver.findElement(By.id("search-field-keyword")).sendKeys(keyword);
		
		String location ="22102";
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(location);
		
		driver.findElement(By.id("findTechJobs")).click();
		
		if(driver.getTitle().contains("Jobs not found | Dice.com") ) {
			
			count="0";
		
		}else {
			count = driver.findElement(By.id("posiCountId")).getText();//compile err
			
		}
		
		System.out.println(count);
		count= count.replaceAll(",",""); //Integer.parseInt("123")
		if(Integer.valueOf(count)>=0) {
			
			
			s1.set(i, s1.get(i)+ "-" + count);
			
			System.out.println("There are more then a 1000 position for java developres");
		}else {
			System.out.println("Well too bad bro, better start learning VBScript ;)");
		}
		
		driver.navigate().back();
		
		//TIllllllll
		}
		
		
		driver.close();
		
		System.out.println(s1);
		System.out.println("Step completed " + LocalDateTime.now());
		// ADDED NEW LINE
		//ANOTHER LINE
		
		
	}

}
