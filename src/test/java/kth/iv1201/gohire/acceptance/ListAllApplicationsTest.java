package kth.iv1201.gohire.acceptance;

import kth.iv1201.gohire.acceptance.util.WebdriverConfigurer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class for Selenium WebDriver-based automated web testing of listing applicants functionality.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
@Sql(scripts = "classpath:acceptance-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ListAllApplicationsTest {
    @LocalServerPort
    private int port;
    private static LinkedList<Class<? extends WebDriver>> availableBrowserWebDrivers;
    private String startingPointURL;
    private static final int IMPLICIT_WAIT_SECONDS = WebdriverConfigurer.IMPLICIT_WAIT_SECONDS;

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

    private void doLogin(WebDriver webDriver) {
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validRecruiterUser");
        passwordInput.sendKeys("validRecruiterPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfRecruiterCanChooseToListApplications (WebDriver webDriver) {
        doLogin(webDriver);

        WebElement showApplicantsButton = webDriver.findElement(By.id("showApplications"));
        webDriver.quit();
        assertNotNull(showApplicantsButton, "Could not find show applicants button");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfListOfApplicantsAreShownWithStatusesForEachApplicant (WebDriver webDriver) {
        doLogin(webDriver);

        WebElement showApplicantsButton = webDriver.findElement(By.id("showApplications"));
        showApplicantsButton.click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));

        boolean unhandledExists = false;
        boolean acceptecExists = false;
        boolean rejectedExists = false;
        String status;

        for(int i = 0; i < 4; i++) {
            try {
                status = webDriver.findElement(By.id("application-listing-" + i + "-status")).getText();
                if(status.contains("Unhandled"))
                    unhandledExists = true;
                if(status.contains("Accepted"))
                    acceptecExists = true;
                if(status.contains("Rejected"))
                    rejectedExists = true;
            } catch (Exception exception) {
                System.out.println("Applicant list fully checked.");
            }
        }
        webDriver.quit();
        assertTrue(unhandledExists && acceptecExists && rejectedExists, "The status of applicants is not visible");
    }

}
