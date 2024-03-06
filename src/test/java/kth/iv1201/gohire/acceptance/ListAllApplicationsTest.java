package kth.iv1201.gohire.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.LinkedList;
import java.util.stream.Stream;

import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.determineAvailableBrowserWebDrivers;
import static kth.iv1201.gohire.acceptance.util.WebdriverConfigurer.fetchWebDrivers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
@Sql(scripts = "classpath:acceptance-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ListAllApplicationsTest {
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


}
