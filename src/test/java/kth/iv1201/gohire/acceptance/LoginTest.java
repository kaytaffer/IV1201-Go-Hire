package kth.iv1201.gohire.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for Selenium WebDriver-based automated web testing of login functionality.
 */
public class LoginTest {

    private WebDriver webDriver;
    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @ParameterizedTest
    @ValueSource(classes = {ChromeDriver.class})
        //@ValueSource(classes = { ChromeDriver.class, FirefoxDriver.class, EdgeDriver.class})
    void testSuccessfulLoginWithValidCredentials(Class<? extends WebDriver> webDriverClass) {
        webDriver = WebDriverManager.getInstance(webDriverClass).create();
        webDriver.get("http://localhost:8080/login");

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validApplicantUser");
        passwordInput.sendKeys("validApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String loggedInUser = webDriver.findElement(By.id("caption")).getText();
        assertTrue(loggedInUser.contains("Applicant"), "The expected caption text does not appear.");

    }

    @ParameterizedTest
    @ValueSource(classes = {ChromeDriver.class})
        //@ValueSource(classes = { ChromeDriver.class, FirefoxDriver.class, EdgeDriver.class})
    void testInvalidLoginShowsErrorMessage(Class<? extends WebDriver> webDriverClass) {
        webDriver = WebDriverManager.getInstance(webDriverClass).create();
        webDriver.get("http://localhost:8080/login");

        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));
        usernameInput.sendKeys("invalidApplicantUser");
        passwordInput.sendKeys("invalidApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userNotice = webDriver.findElement(By.id("user-notice")).getText();
        assertTrue(userNotice.contains("Username and password do not match."), "The expected message does not appear.");
    }

    @ParameterizedTest
    @ValueSource(classes = {ChromeDriver.class})
        //@ValueSource(classes = { ChromeDriver.class, FirefoxDriver.class, EdgeDriver.class})
    void testEmptyFieldsTriggerErrorMessage(Class<? extends WebDriver> webDriverClass) {

        webDriver = WebDriverManager.getInstance(webDriverClass).create();
        webDriver.get("http://localhost:8080/login");

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userNotice = webDriver.findElement(By.id("user-notice")).getText();
        assertTrue(userNotice.contains("Please follow the form's required input examples."), "The expected message does not appear.");
    }
}
