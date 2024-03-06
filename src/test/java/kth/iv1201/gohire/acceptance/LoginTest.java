package kth.iv1201.gohire.acceptance;

import kth.iv1201.gohire.acceptance.util.WebdriverConfigurer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Stream;

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for Selenium WebDriver-based automated web testing of login functionality.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(ExecutionMode.SAME_THREAD)
@ActiveProfiles("test")
@Sql(scripts = "classpath:acceptance-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) //TODO ExecutionPhase before class or test?
public class LoginTest {
    @LocalServerPort
    private int port;
    private static LinkedList<Class<? extends WebDriver>> availableBrowserWebDrivers;
    private String startingPointURL;

    @BeforeAll
    static void setUpAll() {
        availableBrowserWebDrivers = determineAvailableBrowserWebDrivers();
    }

    @BeforeEach
    void setUp() {
        startingPointURL = "http://localhost:" + port + "/login";
    }

    @AfterAll
    static void tearDownAll() {
        availableBrowserWebDrivers = null;
    }

    private static Stream<WebDriver> provideTestWithWebDrivers() {
        return fetchWebDrivers(availableBrowserWebDrivers);
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testSuccessfulLoginWithValidApplicantCredentials(WebDriver webDriver) {
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);

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
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);
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
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);

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
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);
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
        WebdriverConfigurer.goToAndAwait(webDriver, "http://localhost:" + port + "/");
        String url = webDriver.getCurrentUrl();
        webDriver.quit();
        assertEquals(startingPointURL, url, "URL doesn't match expectation");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfRequestedUrlIsCorrectAfterLogin(WebDriver webDriver) {
        String requestedUrl = "http://localhost:" + port + "/";
        WebdriverConfigurer.goToAndAwait(webDriver, requestedUrl);

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
