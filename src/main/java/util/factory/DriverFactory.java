package util.factory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Generic factory with a static function that will return an appropriate instance of driver for the platform specified
 * by the testmodel. You will need to include the server of the test service for remote tests.
 */
public class DriverFactory {

    public static <T extends RemoteWebDriver> T getDriver(DesiredCapabilities capabilities) {

        try {
                if (capabilities.getCapability("platformName").toString().toLowerCase().equals("android"))
                {
                    AndroidDriver androidDriver = new AndroidDriver(
                            new URL(capabilities.getCapability("testServer").toString()), capabilities);
                    return (T) androidDriver;
                }

                else if (capabilities.getCapability("platformName").toString().toLowerCase().equals("ios"))
                {
                    IOSDriver iosDriver = new IOSDriver(
                            new URL(capabilities.getCapability("testServer").toString()), capabilities);
                    return (T) iosDriver;
                }
                // Give us a generic RemoteWebDriver if we can't determine that it is an iOS or Android type driver (Appium)
                else
                {
                    RemoteWebDriver webDriver = new RemoteWebDriver(
                            new URL(capabilities.getCapability("testServer").toString()), capabilities);
                    return (T) webDriver;
                }

        }
        catch (MalformedURLException e) {}
        return null;
    }
}