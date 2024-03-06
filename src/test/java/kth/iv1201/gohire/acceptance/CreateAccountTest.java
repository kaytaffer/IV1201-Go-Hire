package kth.iv1201.gohire.acceptance;

import kth.iv1201.gohire.acceptance.util.WebdriverConfigurer;
import org.junit.jupiter.api.*;
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

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.determineAvailableBrowserWebDrivers;
import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.fetchWebDrivers;


/**
 * A class for Selenium WebDriver-based automated web testing of account creation functionality.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(ExecutionMode.SAME_THREAD)
@ActiveProfiles("test")
@Sql(scripts = "classpath:acceptance-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CreateAccountTest {
    @LocalServerPort
    private int port;
    private static LinkedList<Class<? extends WebDriver>> availableBrowserWebDrivers;
    private static String startingPointURL;

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
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testIfUserCanCreateNewApplicantSuccessfully(WebDriver webDriver) {
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);

        WebElement firstName = webDriver.findElement(By.id("create-applicant-form-first-name"));
        WebElement lastName = webDriver.findElement(By.id("create-applicant-form-last-name"));
        WebElement email = webDriver.findElement(By.id("create-applicant-form-email"));
        WebElement personNumber = webDriver.findElement(By.id("create-applicant-form-person-number"));
        WebElement username = webDriver.findElement(By.id("create-applicant-form-username"));
        WebElement password = webDriver.findElement(By.id("create-applicant-form-password"));
        WebElement submitButton = webDriver.findElement(By.id("create-applicant-form-submit"));

        firstName.sendKeys("newApplicant");
        lastName.sendKeys("newApplicant");
        email.sendKeys("newApplicant@kth.se");
        personNumber.sendKeys("19909090-9090");
        username.sendKeys("newApplicant");
        password.sendKeys("newPassword");
        submitButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userNotice = webDriver.findElement(By.id("user-notice")).getText();
        webDriver.quit();

        Assertions.assertTrue(userNotice.contains("Account successfully created"), "The expected message does not appear.");
    }
}

