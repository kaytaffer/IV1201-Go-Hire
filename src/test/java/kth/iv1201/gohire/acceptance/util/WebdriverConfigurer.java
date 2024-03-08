package kth.iv1201.gohire.acceptance.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Contains static methods to supply acceptance test classes with <code>WebDriver</code>s suited to the system on which
 * the tests are run.
 */
public class WebdriverConfigurer {

    public static final int IMPLICIT_WAIT_SECONDS = 2;

    //This method contains a list of all classes of WebDrivers considered possible and relevant to run.
    private static LinkedList<Class<? extends WebDriver>> possibleBrowsersToUseForTest() {
        LinkedList<Class<? extends WebDriver>> possibleBrowsersToUseForTests = new LinkedList<>();
        possibleBrowsersToUseForTests.add(ChromeDriver.class);
        possibleBrowsersToUseForTests.add(FirefoxDriver.class);
        possibleBrowsersToUseForTests.add(EdgeDriver.class);
        possibleBrowsersToUseForTests.add(SafariDriver.class);
        //Add more driver classes here as required
        return possibleBrowsersToUseForTests;
    }

    /**
     * Attempts to instantiate <code>WebDriver</code>s for possible browser types. The available browsers are added to
     * the return <code>LinkedList</code> and unavailable ones are written to <code>System.out</code>.
     * @return A <code>LinkedList</code> containing the classes of the <code>WebDriver</code>s possible to instantiate
     * on the running machine.
     */
    public static LinkedList<Class<? extends WebDriver>> determineAvailableBrowserWebDrivers() {
        LinkedList<Class<? extends WebDriver>> availableDrivers = new LinkedList<>();
        for(Class<? extends WebDriver> candidateBrowser : possibleBrowsersToUseForTest()) {
            try {
                WebDriver driver = WebDriverManager.getInstance(candidateBrowser).create();
                availableDrivers.add(candidateBrowser);
                driver.quit();
            } catch (WebDriverManagerException exception) {
                System.out.println("Notice! Webdriver for " + candidateBrowser + " was not instantiated. No " +
                        "tests will run with this browser.");
            }
        }
        return availableDrivers;
    }

    /**
     * Creates a <code>Stream</code> of instantiated <code>WebDriver</code>s.
     * @param driverClasses the classes for which to instantiate <code>WebDriver</code>s.
     * @return The instantiated and prepared <code>WebDriver</code>s.
     */
    public static Stream<WebDriver> fetchWebDrivers(LinkedList<Class<? extends WebDriver>> driverClasses) {
        Stream.Builder<WebDriver> streamBuilder = Stream.builder();
        for(Class<? extends WebDriver> candidateDriver : driverClasses) {
            WebDriver driver = WebDriverManager.getInstance(candidateDriver).create();
            streamBuilder.add(driver);
        }
        return streamBuilder.build();
    }

    /**
     * Tasks the driver to get the page at the supplied path and implicitly wait 5 seconds.
     * @param driver The driver to task.
     * @param path the desired URL-path.
     */
    public static void goToAndAwait(WebDriver driver, String path) {
        driver.get(path);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
    }


}
