package com.dice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobWithTextFile {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter how many random componies you want to search for on Dice");
		
		int numberOfComp = Integer.parseInt(input.nextLine());
		// Creating text file with test data in it:
		String txtFileList = "C:\\Users\\Yaroslav Kryvda\\Desktop\\JAVA\\Java Codes\\maven-test-automatio\\textTestInput";
		
		
		 PrintWriter testDataFile = new PrintWriter(txtFileList);
		 Faker c1 = new Faker();
		 
		 // Setting 5 lines of data
		 for(int i=0; i<numberOfComp; i++) {
			 testDataFile.println(c1.company().name());
			}
		 	testDataFile.close();
		 
		 //Creating Array where we store our testData
		 List<String> s1 = new ArrayList();
		 //Creating buffer so we can read our newly created textFile and set to our Array List.
		 File file = new File(txtFileList);
		 BufferedReader br = new BufferedReader(new FileReader(file));
		 String theLine;

		 while ((theLine = br.readLine()) != null)
			  s1.add(theLine);
		
		
		 
		 //Creating PrintWriter in order to overWrite our created txt
		
		
		PrintWriter out = new PrintWriter(txtFileList);
		
		//Creating count string to store the Dice Search result.
		  String count;
			
		
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
		
		String location ="22311";
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(location);
		
		driver.findElement(By.id("findTechJobs")).click();
		
		if(driver.getTitle().contains("Jobs not found | Dice.com") ) {
			
			count="0";
			out.println(s1.get(i)+": "+count); 
		}else {
			count = driver.findElement(By.id("posiCountId")).getText();//compile err
			out.println(s1.get(i)+": "+count);
		}
		
		System.out.println(count);
		count= count.replaceAll(",",""); //Integer.parseInt("123")
		
		//Re setting my Array List
		if(Integer.valueOf(count)>=0) {	
			s1.set(i, s1.get(i)+ "-" + count);
	
			System.out.println("There are more then 0 position for java developres");
		}else {
			System.out.println("Well too bad bro, better start learning VBScript ;)");
		}
		
		driver.navigate().back();
		
		//TIllllllll
		}
		
		
		driver.close();
		out.close();
		
		System.out.println(s1);
		System.out.println("Step completed " + LocalDateTime.now());
		// ADDED NEW LINE
		//ANOTHER LINE
		
		
	}
}
