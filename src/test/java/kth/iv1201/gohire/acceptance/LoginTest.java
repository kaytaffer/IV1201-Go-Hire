package kth.iv1201.gohire.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Stream;

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurator.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for Selenium WebDriver-based automated web testing of login functionality.
 */
@Execution(ExecutionMode.SAME_THREAD)
public class LoginTest {
    private static LinkedList<Class<? extends WebDriver>> availableBrowserWebDrivers;
    private final static String startingPointURL = "http://localhost:8080/login";

    @BeforeAll
    static void setUpAll() {
        availableBrowserWebDrivers = determineAvailableBrowserWebDrivers();
    }

    @AfterAll
    static void tearDownAll() {
        availableBrowserWebDrivers = null;
    }

    private static Stream<WebDriver> provideTestWithWebDrivers() {
        return fetchWebDrivers(availableBrowserWebDrivers, startingPointURL);
    }
    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testSuccessfulLoginWithValidApplicantCredentials(WebDriver webDriver) {
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validApplicantUser");
        passwordInput.sendKeys("validApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String loggedInUser = webDriver.findElement(By.id("caption")).getText();
        webDriver.quit();
        assertTrue(loggedInUser.contains("Applicant"), "The expected caption text does not appear.");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testSuccessfulLoginWithValidRecruiterCredentials(WebDriver webDriver) {
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validRecruiterUser");
        passwordInput.sendKeys("validRecruiterPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String loggedInUser = webDriver.findElement(By.id("caption")).getText();
        webDriver.quit();
        assertTrue(loggedInUser.contains("Recruiter"), "The expected caption text does not appear.");
    }
    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testInvalidLoginShowsErrorMessage(WebDriver webDriver) {
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));
        usernameInput.sendKeys("invalidApplicantUser");
        passwordInput.sendKeys("invalidApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userNotice = webDriver.findElement(By.id("user-notice")).getText();
        webDriver.quit();
        assertTrue(userNotice.contains("Username and password do not match."), "The expected message does not appear.");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testEmptyFieldsTriggerErrorMessage(WebDriver webDriver) {
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userNotice = webDriver.findElement(By.id("user-notice")).getText();
        webDriver.quit();
        assertTrue(userNotice.contains("Please follow the form's required input examples."), "The expected message does not appear.");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfUserIsRedirectedToLoginPageIfNotLoggedIn(WebDriver webDriver) {
        webDriver.get("http://localhost:8080/");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String url = webDriver.getCurrentUrl();
        webDriver.quit();
        assertEquals(startingPointURL, url, "URL doesn't match expectation");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfRequestedUrlIsCorrectAfterLogin(WebDriver webDriver) {
        String requestedUrl = "http://localhost:8080/";
        webDriver.get(requestedUrl);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validApplicantUser");
        passwordInput.sendKeys("validApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        webDriver.findElement(By.id("caption")).getText(); //stalling a bit
        String url = webDriver.getCurrentUrl();
        webDriver.quit();
        assertEquals(requestedUrl, url, "URL doesn't match expectation");
    }

}
