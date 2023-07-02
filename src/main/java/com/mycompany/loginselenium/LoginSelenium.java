package com.mycompany.loginselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;       
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Assertions;
import java.time.Duration;
/**
 *
 * Programmer: Lance Asher M. Elloso
 * Date: July 1, 2023
 * Purpose: Create a program using java and selenium library that 
 * will test the login functionality of https://www.saucedemo.com/
 * Simple and Easy Version using my local machine
 *  
 */

//Reference to setup selenium in netbanes IDE https://www.contradodigital.com/2021/02/25/how-to-setup-selenium-using-java-and-apache-netbeans-for-automated-web-browser-testing/

public class LoginSelenium {
    protected RemoteWebDriver driver;

    public static void main(String[] args) {
        //System.setProperty("webdriver.chrome.driver", "D:\\Documents\\NetBeansProjects\\LoginSelenium\\src\\main\\java\\chromedriver_win32\\chromedriver.exe"); //Change depe
        
        String projectPath = System.getProperty("user.dir");
        String driverPath = projectPath + "/src/main/java/chromedriver_win32/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
         
        // Scenario 1: Log in using standard user
        driver.get("https://www.saucedemo.com/");

        // Enter username and password for standard user through finder method
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        // Click on the login button
        driver.findElement(By.id("login-button")).click();
        
        // Confirm that the user is able to login
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

            // Reference for login: https://docs.saucelabs.com/web-apps/automated-testing/selenium/
        
        // Verify that user is able to navigate into the home page
        WebElement inventoryElement = driver.findElement(By.className("title"));
        
        String titleText = inventoryElement.getText();
        
        if (titleText.equals("Products")) {
            System.out.println("Scenario 1: User is able to navigate to home page - PASSED");
        } else {
            System.out.println("Scenario 1: User is unable to navigate to home page - FAILED");
        }
        
        // Log out
        driver.findElement(By.id("react-burger-menu-btn")).click();
        
        // Use explicit wait before the logout button to be interactable 
        // As recommended by sauce lab's documentation. It is better to use explicit wait than implicit since implicit.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        driver.findElement(By.id("logout_sidebar_link")).click();
        
        // Verify that user is navigated to login page
        Assertions.assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
        
        // Scenario 2: Log in using locked out user
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify error message for locked out user
        WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
        String errorMessage = errorElement.getText();
        
        if (errorMessage.equals("Epic sadface: Sorry, this user has been locked out.")) {
            System.out.println("Scenario 2: Error message displayed correctly for locked out user - PASSED");
        } else {
            System.out.println("Scenario 2: Error message not displayed correctly for locked out user - FAILED");
        }

        // Close the browser
        driver.quit();
    }
    
 
}
