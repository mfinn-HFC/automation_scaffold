package util.factory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.firefox.internal.Executable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Generic factory with a static function that will return an appropriate instance of driver for the platform specified
 * by the testmodel. You will need to include the server of the test service for remote tests.
 */
public class DriverFactory {

    public static RemoteWebDriver getDrivers(DesiredCapabilities capabilities, String testServer, Class clazz) throws MalformedURLException {

        Map<String, ?> capsMap = capabilities.asMap();
            try
            {
                // Set test name if running in the cloud
                if(capsMap.containsKey("testdroid_testrun"))
                {
                    capabilities.setCapability("testdroid_testrun", clazz.getSimpleName().toString());
                }

                // Try and get one of the three driver types
                if (capsMap.containsValue("android"))
                {
                    AndroidDriver androidDriver = new AndroidDriver(
                            new URL(testServer), capabilities);
                    return androidDriver;

                }
                else if (capsMap.containsValue("ios"))
                {
                    IOSDriver iosDriver = new IOSDriver(
                            new URL(testServer), capabilities);
                    return iosDriver;
                }
                else
                {
                    try
                    {
                        // Give us a generic RemoteWebDriver if we can't determine that it is an iOS or Android type driver (Appium)
                        RemoteWebDriver webDriver = new RemoteWebDriver(
                                new URL(testServer), capabilities);
                        return webDriver;
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        System.out.println("No driver created! Are your capabilities in the correct format?");
        return null;
    }
}