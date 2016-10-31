package util.factory;

import com.applitools.eyes.Eyes;
import com.applitools.eyes.MatchLevel;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 */
public class EyesProvider {

    private Eyes eyes;
    private String appName;
    private RemoteWebDriver driver;
    private String testName;
    private final String apiKey = "";

    public EyesProvider(RemoteWebDriver driver, String appName, String testName) {
        this.driver = driver;
        this.appName = appName;
        this.testName = testName;
    }

    public Eyes getEyes() {
        eyes = new Eyes();
        eyes.setApiKey(apiKey);
        eyes.setMatchLevel(MatchLevel.STRICT);
        // The first time you run on an environment, setting this to false will accept the 1st run as base line
        eyes.setSaveNewTests(false);
        eyes.open(driver, appName, testName);
        return eyes;
    }

    public void endEyes() {
        eyes.close();
        eyes.abortIfNotClosed();
    }
}
