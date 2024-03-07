package kth.iv1201.gohire.acceptance;

import io.github.bonigarcia.wdm.WebDriverManager;
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
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Stream;

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.determineAvailableBrowserWebDrivers;
import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.fetchWebDrivers;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for Selenium WebDriver-based automated web testing of handling a single applicant.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
@Sql(scripts = "classpath:acceptance-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ShowApplicationTest {
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

    private void doShowApplicants(WebDriver webDriver) {
        WebdriverConfigurer.goToAndAwait(webDriver, startingPointURL);
        WebElement usernameInput = webDriver.findElement(By.id("login-form-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-form-password"));

        usernameInput.sendKeys("validRecruiterUser");
        passwordInput.sendKeys("validRecruiterPassword");
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement showApplicantsButton = webDriver.findElement(By.id("showApplications"));
        showApplicantsButton.click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfRecruiterCanChooseSingleApplicationToShow(WebDriver webDriver) {
        doShowApplicants(webDriver);

        WebElement button = null;
        for(int i = 0; i < 3; i++) {
            try {
                button = webDriver.findElement(By.id("application-listing-" + i + "-button"));
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
        if(button != null) {
            String text = button.getText();
            assertEquals("handle", text,"Expected button label not found");
        }
        else fail("Could not find button to handle applications.");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfFullViewOfJobApplicationIsShown(WebDriver webDriver) {
        doShowApplicants(webDriver);

        WebElement button = null;
        for(int i = 0; i < 3; i++) {
            try {
                button = webDriver.findElement(By.id("application-listing-" + i + "-button"));
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
        if(button != null) {
            button.click();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement popup = webDriver.findElement(By.id("single-application"));
            assertNotNull(popup, "Could not find full view of job application");
        }
        else fail("Could not find button to handle applications.");
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testIfRecruiterCanAcceptApplicationStatus(WebDriver webDriver) {
        doShowApplicants(webDriver);

        for(int i = 0; i < 3; i++) {
            try {
                webDriver.findElement(By.id("application-listing-" + i + "-button")).click();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                Select dropdown = new Select(webDriver.findElement(By.id("handle-application-form-new-status")));
                WebElement username = webDriver.findElement(By.id("handle-application-form-username"));
                WebElement password = webDriver.findElement(By.id("handle-application-form-password"));
                WebElement submit = webDriver.findElement(By.id("handle-application-form-submit"));
                dropdown.selectByValue("accepted");
                username.sendKeys("validRecruiterUser");
                password.sendKeys("validRecruiterPassword");
                submit.click();
                Thread.sleep(500);

                String status = webDriver.findElement(By.id("application-listing-" + i + "-status")).getText();
                webDriver.quit();
                assertEquals("accepted", status, "Wrong status after change made");
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testIfRecruiterCanRejectApplicationStatus(WebDriver webDriver) {
        doShowApplicants(webDriver);

        for(int i = 0; i < 3; i++) {
            try {
                webDriver.findElement(By.id("application-listing-" + i + "-button")).click();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                Select dropdown = new Select(webDriver.findElement(By.id("handle-application-form-new-status")));
                WebElement username = webDriver.findElement(By.id("handle-application-form-username"));
                WebElement password = webDriver.findElement(By.id("handle-application-form-password"));
                WebElement submit = webDriver.findElement(By.id("handle-application-form-submit"));
                dropdown.selectByValue("rejected");
                username.sendKeys("validRecruiterUser");
                password.sendKeys("validRecruiterPassword");
                submit.click();
                Thread.sleep(500);

                String status = webDriver.findElement(By.id("application-listing-" + i + "-status")).getText();
                webDriver.quit();
                assertEquals("rejected", status, "Wrong status after change made");
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testIfChangeApplicantStatusDeniedIfWrongPasswordIsSupplied(WebDriver webDriver) {
        doShowApplicants(webDriver);

        for(int i = 0; i < 3; i++) {
            try {
                webDriver.findElement(By.id("application-listing-" + i + "-button")).click();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                Select dropdown = new Select(webDriver.findElement(By.id("handle-application-form-new-status")));
                WebElement username = webDriver.findElement(By.id("handle-application-form-username"));
                WebElement password = webDriver.findElement(By.id("handle-application-form-password"));
                WebElement submit = webDriver.findElement(By.id("handle-application-form-submit"));
                dropdown.selectByValue("rejected");
                username.sendKeys("validRecruiterUser");
                password.sendKeys("invalidPassword");
                submit.click();
                Thread.sleep(500);

                try {
                    String userNotice = webDriver.findElement(By.id("user-notice")).getText();
                    assertTrue(userNotice.contains("do not match"), "Wrong user notice message.");
                } catch (Exception exception) {
                    fail("No user notice found");
                }
                webDriver.quit();
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testIfSystemAbortsAlreadyHandledApplication(WebDriver slowRecruiterDriver) {
        WebDriver quickRecruiterDriver = WebDriverManager.getInstance(slowRecruiterDriver.getClass()).create();
        doShowApplicants(slowRecruiterDriver);
        doShowApplicants(quickRecruiterDriver);

        for(int i = 0; i < 3; i++) {
            try {
                slowRecruiterDriver.findElement(By.id("application-listing-" + i + "-button")).click();
                slowRecruiterDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                quickRecruiterDriver.findElement(By.id("application-listing-" + i + "-button")).click();
                quickRecruiterDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                Select dropdown = new Select(quickRecruiterDriver.findElement(By.id("handle-application-form-new-status")));
                WebElement username = quickRecruiterDriver.findElement(By.id("handle-application-form-username"));
                WebElement password = quickRecruiterDriver.findElement(By.id("handle-application-form-password"));
                WebElement submit = quickRecruiterDriver.findElement(By.id("handle-application-form-submit"));
                dropdown.selectByValue("rejected");
                username.sendKeys("validRecruiterUser");
                password.sendKeys("validRecruiterPassword");
                submit.click();
                Thread.sleep(500);
                quickRecruiterDriver.quit();

                dropdown = new Select(slowRecruiterDriver.findElement(By.id("handle-application-form-new-status")));
                username = slowRecruiterDriver.findElement(By.id("handle-application-form-username"));
                password = slowRecruiterDriver.findElement(By.id("handle-application-form-password"));
                submit = slowRecruiterDriver.findElement(By.id("handle-application-form-submit"));
                dropdown.selectByValue("rejected");
                username.sendKeys("validRecruiterUser");
                password.sendKeys("validRecruiterPassword");
                submit.click();
                Thread.sleep(500);

                try {
                    String userNotice = slowRecruiterDriver.findElement(By.id("user-notice")).getText();
                    assertTrue(userNotice.contains("been handled"), "Wrong user notice message.");
                } catch (Exception exception) {
                    fail("No user notice found");
                }
                slowRecruiterDriver.quit();
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
    }

    @ParameterizedTest
    @Execution(ExecutionMode.SAME_THREAD)
    @MethodSource("provideTestWithWebDrivers")
    void testIfRecruiterCanGoBackToApplicationList(WebDriver webDriver) {
        doShowApplicants(webDriver);

        WebElement button = null;
        for(int i = 0; i < 3; i++) {
            try {
                button = webDriver.findElement(By.id("application-listing-" + i + "-button"));
            } catch (Exception exception) {
                System.out.println("Attempting to find button.");
            }
        }
        if(button != null) {
            button.click();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement close = webDriver.findElement(By.className("close-popup"));
            close.click();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            try {
                webDriver.findElement(By.id("single-application"));
                fail("Single application still visible");
            } catch (Exception exception) {
                assertTrue(true);
            }
        }
        webDriver.quit();
    }

}