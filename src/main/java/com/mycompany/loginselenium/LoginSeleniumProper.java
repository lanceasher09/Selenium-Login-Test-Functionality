
package com.mycompany.loginselenium;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * Programmer: Lance Asher M. Elloso
 * Date: July 2, 2023
 * Purpose: Create a program using java and selenium library that 
 * will test the login functionality of https://www.saucedemo.com/
 * Proper and Advanced Version implemented codes from the documentation of Sauce Labs using my local machine
 *  
 */
public class LoginSeleniumProper {
   
    protected RemoteWebDriver driver;

    public static void main(String[] args) {
        
        System.setProperty("webdriver.chrome.driver", "D:\\Documents\\NetBeansProjects\\LoginSelenium\\src\\main\\java\\chromedriver_win32\\chromedriver.exe");
        
        //Instantiate the WebDriver ojbect with variable driver
        WebDriver driver = new ChromeDriver();
        
        driver.get("https://www.saucedemo.com");
        
        // Located HTML Elements on the web page through CSS Selector
        By usernameFieldLocator = By.cssSelector("#user-name");
        By passwordFieldLocator = By.cssSelector("#password");
        By submitButtonLocator = By.cssSelector(".btn_action");
        
        // Perform an Explicit Wait until username field is located.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameFieldLocator));
        
        // Finder Methods
        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);
        
        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        submitButton.click();
        
        // Verify that the user is able to navigate into the home page
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
        
        // Verify that user is able to navigate to home page
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
        // As recommended by sauce lab's documentation. It is better to use explicit wait than implicit.
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        driver.findElement(By.id("logout_sidebar_link")).click();
        
        //Verify that user is navigated to login page
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