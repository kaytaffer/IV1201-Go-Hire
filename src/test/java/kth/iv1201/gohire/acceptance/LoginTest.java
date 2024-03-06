package kth.iv1201.gohire.acceptance;

import kth.iv1201.gohire.DTO.CreateApplicantRequestDTO;
import kth.iv1201.gohire.acceptance.util.WebdriverConfigurer;
import kth.iv1201.gohire.entity.PersonEntity;
import kth.iv1201.gohire.entity.RoleEntity;
import kth.iv1201.gohire.repository.ApplicationStatusRepository;
import kth.iv1201.gohire.repository.PersonRepository;
import kth.iv1201.gohire.repository.RoleRepository;
import kth.iv1201.gohire.service.PersonService;
import kth.iv1201.gohire.service.exception.UserCreationFailedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Stream;

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class for Selenium WebDriver-based automated web testing of login functionality.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
@Transactional
//@Sql(scripts = "classpath:import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Rolls back the test db state.
public class LoginTest {
    @LocalServerPort
    private int port;
    private static LinkedList<Class<? extends WebDriver>> availableBrowserWebDrivers;
    private String startingPointURL;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ApplicationStatusRepository applicationStatusRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PersonService personService;
    CreateApplicantRequestDTO createApplicantRequestDTO;
    CreateApplicantRequestDTO createRecruiterRequestDTO;

    @BeforeAll
    static void setUpAll() {
        availableBrowserWebDrivers = determineAvailableBrowserWebDrivers();
    }

    @BeforeEach
    void setUp() throws UserCreationFailedException {
        startingPointURL = "http://localhost:" + port + "/login";
        /*
        PersonEntity loadedUser = personRepository.findByUsername("validApplicantUser");
        System.out.println("\n\n\n" + loadedUser.getUsername() + ": " + loadedUser.getPassword() + "\n\n\n");
        */
        createApplicantRequestDTO = new CreateApplicantRequestDTO("Applicant", "Lastname",
                "applicant@kth.se", "19909090-9090", "applicant1", "applicantPW");
        personService.createApplicantAccount(createApplicantRequestDTO);
        createRecruiterRequestDTO = new CreateApplicantRequestDTO("Recruiter", "Surname",
                "recruiter@kth.se", "19909090-9090", "recruiter1", "recruiterPW");
        personService.createApplicantAccount(createRecruiterRequestDTO);
        RoleEntity role = roleRepository.findRoleById(1);
        PersonEntity recruiter = personRepository.findByUsername("recruiter1");
        recruiter.setRole(role);
        personRepository.save(recruiter);
    }

    @AfterEach
    void tearDown() {
        PersonEntity applicant = personRepository.findByUsername(createApplicantRequestDTO.getUsername());
        personRepository.delete(applicant);
        PersonEntity recruiter = personRepository.findByUsername(createRecruiterRequestDTO.getUsername());
        personRepository.delete(recruiter);
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

        usernameInput.sendKeys(createApplicantRequestDTO.getUsername());
        passwordInput.sendKeys(createApplicantRequestDTO.getPassword());
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String loggedInUser = "";
        try {
            loggedInUser = webDriver.findElement(By.id("caption")).getText();
        } catch (NoSuchElementException exception) {
            fail("Cannot find element with id " + "caption");
        }
        webDriver.quit();
        assertTrue(loggedInUser.contains("Applicant"), "The expected caption text does not appear.");
    }

    @ParameterizedTest
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

        String loggedInUser = "";
        try {
            loggedInUser = webDriver.findElement(By.id("caption")).getText();
        } catch (NoSuchElementException exception) {
            fail("Cannot find element with id " + "caption");
        }
        webDriver.quit();
        assertTrue(loggedInUser.contains("Recruiter"), "The expected caption text does not appear.");
    }

    @ParameterizedTest
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
    @MethodSource("provideTestWithWebDrivers")
    void testIfUserIsRedirectedToLoginPageIfNotLoggedIn(WebDriver webDriver) {
        WebdriverConfigurer.goToAndAwait(webDriver, "http://localhost:" + port + "/");
        String url = webDriver.getCurrentUrl();
        webDriver.quit();
        assertEquals(startingPointURL, url, "URL doesn't match expectation");
    }

    @ParameterizedTest
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
        try {
            webDriver.findElement(By.id("caption")).getText();
        } catch (NoSuchElementException exception) {
            fail("Cannot find element with id " + "caption");
        }
        String url = webDriver.getCurrentUrl();
        webDriver.quit();
        assertEquals(requestedUrl, url, "URL doesn't match expectation");
    }

}
