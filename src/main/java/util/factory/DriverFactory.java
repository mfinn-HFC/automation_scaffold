package util.factory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.firefox.internal.Executable;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Generic factory with a static function that will return an appropriate instance of driver for the platform specified
 * by the testmodel. You will need to include the server of the test service for remote tests.
 */
public class DriverFactory {

    public static Object[][] getDrivers(List<DesiredCapabilities> capabilitiesList) throws MalformedURLException {

        int listSize = capabilitiesList.size();
        final Object[] driverArray = new Object[listSize];
        int index = 0;

        for (DesiredCapabilities caps : capabilitiesList) {
            final Object[] innerArray = new Object[1];
            try {
                if (caps.getCapability("platformName").toString().toLowerCase().equals("android")) {
                    AndroidDriver androidDriver = new AndroidDriver(
                            new URL(caps.getCapability("testServer").toString()), caps);
                    innerArray[0] = androidDriver;

                } else if (caps.getCapability("platformName").toString().toLowerCase().equals("ios")) {
                    IOSDriver iosDriver = new IOSDriver(
                            new URL(caps.getCapability("testServer").toString()), caps);
                    innerArray[0] = iosDriver;
                }
            } catch (Exception e) {
            }

            try {
                // Give us a generic RemoteWebDriver if we can't determine that it is an iOS or Android type driver (Appium)
                RemoteWebDriver webDriver = new RemoteWebDriver(
                        new URL(caps.getCapability("testServer").toString()), caps);
                innerArray[0] = webDriver;

                driverArray[index] = innerArray;
                index++;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        Object[][] finalArray = new Object[driverArray.length][1];
        int arrayIndex = 0;
        for(Object obj : driverArray)
        {
            finalArray[arrayIndex] = new Object[]{obj};
            arrayIndex++;
        }

        return finalArray;
    }
}