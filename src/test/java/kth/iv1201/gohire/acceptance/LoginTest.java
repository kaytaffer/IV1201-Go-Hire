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
 * A class for Selenium WebDriver-based automated web testing of Login use case.
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
    void testSuccessfulLoginWithValidCredentials(WebDriver webDriver) {
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));
        usernameInput.sendKeys("validApplicantUser");
        passwordInput.sendKeys("validApplicantPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        String loggedInUser = webDriver.findElement(By.id("caption")).getText();
        assertTrue(loggedInUser.contains("Applicant"),"The expected caption text does not appear.");
    }

}
