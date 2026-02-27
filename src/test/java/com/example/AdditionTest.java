package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionTest {

    private WebDriver driver;
    private WebDriverWait wait;
    
    // Use file protocol to open HTML directly (no server needed)
    private String getFileUrl() {
        File file = new File("src/main/webapp/index.html");
        String absolutePath = file.getAbsolutePath().replace("\\", "/");
        return "file:///" + absolutePath;
    }

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAddition() {
        System.out.println("Running testAddition...");
        
        // Open the HTML file directly
        String fileUrl = getFileUrl();
        System.out.println("Opening: " + fileUrl);
        driver.get(fileUrl);
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Find elements
        WebElement num1Field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num2Field = driver.findElement(By.id("num2"));
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add'] | //button"));
        
        // Clear and enter values
        num1Field.clear();
        num2Field.clear();
        num1Field.sendKeys("5");
        num2Field.sendKeys("3");
        System.out.println("Entered: 5 and 3");
        
        addButton.click();
        System.out.println("Clicked Add button");
        
        // Wait for result
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        String actualResult = result.getText().trim();
        System.out.println("Result: " + actualResult);
        
        assertTrue(actualResult.contains("8") || actualResult.contains("8.0"), 
                  "Expected result to contain 8, got: " + actualResult);
        
        System.out.println("Test passed!\n");
    }
    
    @Test
    public void testAdditionWithNegativeNumbers() {
        System.out.println("Running testAdditionWithNegativeNumbers...");
        
        driver.get(getFileUrl());
        
        WebElement num1Field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num2Field = driver.findElement(By.id("num2"));
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add'] | //button"));
        
        num1Field.clear();
        num2Field.clear();
        num1Field.sendKeys("-5");
        num2Field.sendKeys("3");
        System.out.println("Entered: -5 and 3");
        
        addButton.click();
        
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        String actualResult = result.getText().trim();
        System.out.println("Result: " + actualResult);
        
        assertTrue(actualResult.contains("-2"), 
                  "Expected result to contain -2, got: " + actualResult);
        
        System.out.println("Test passed!\n");
    }
    
    @Test
    public void testAdditionWithDecimalNumbers() {
        System.out.println("Running testAdditionWithDecimalNumbers...");
        
        driver.get(getFileUrl());
        
        WebElement num1Field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num2Field = driver.findElement(By.id("num2"));
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add'] | //button"));
        
        num1Field.clear();
        num2Field.clear();
        num1Field.sendKeys("5.5");
        num2Field.sendKeys("3.7");
        System.out.println("Entered: 5.5 and 3.7");
        
        addButton.click();
        
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        String actualResult = result.getText().trim();
        System.out.println("Result: " + actualResult);
        
        assertTrue(actualResult.contains("9.2"), 
                  "Expected result to contain 9.2, got: " + actualResult);
        
        System.out.println("Test passed!\n");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}