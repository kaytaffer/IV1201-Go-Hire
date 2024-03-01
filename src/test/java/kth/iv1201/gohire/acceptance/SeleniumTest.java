package kth.iv1201.gohire.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;



/**
 * A class for Selenium WebDriver-based automated web testing.
 */
public class SeleniumTest {

    private WebDriver webDriver;

    @BeforeAll
    static void setUpAll(){
         WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        webDriver = new ChromeDriver();
        webDriver.get("http://localhost:8080/login");
    }
    @AfterEach
    void tearDown() {
        webDriver.quit();
    }
    @Test
    void testIfFirstPageTitleIsCorrect(){
        String title = webDriver.getTitle();
        assertTrue(title.contains("Go Hire"),"The first page title does not match the expected value." );
    }
    
    @Test
    void testSuccessfulLoginWithValidCredentials() throws InterruptedException {
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));
        usernameInput.sendKeys("validApplicantUser");
        passwordInput.sendKeys("validApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("loginButton"));
        loginButton.click();

        String loggedInUser = webDriver.findElement(By.id("loggedInUser")).getText();  //TODO 
        assertTrue(loggedInUser.contains("validUser"),"The logged in user does not match the expected value.");

    }
}
