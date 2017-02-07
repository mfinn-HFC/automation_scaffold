package model;

import enums.SwipeDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by matt-hfc on 2/7/17.
 * A base object for all tests and screen / page objects to inherit from, which will contain generic methods for
 * RemoteWebDriver / Appium
 */
public class BaseObject <T extends RemoteWebDriver> {

    protected T driver;

    // Swipe a mobile screen by a percentage of the screen size, UP or DOWN
    public void swipeByPercent(int percent, SwipeDirection direction)
    {
        if( (driver.getClass() == RemoteWebDriver.class) || (driver.getClass() == WebDriver.class))
        {
            throw new IllegalArgumentException("You cannot use a swipe action with a RemoteWebDriver. You must use " +
                    "an Appium based driver, such as IOSDriver, AndroidDriver or AppiumDriver");
        }

        Dimension dimension = driver.manage().window().getSize();
        int xCenter = dimension.getWidth() / 2;
        int yCenter = dimension.getHeight() / 2;
        int swipeEndPoint;

        // For Appium, down means higher pixel location, up means lower pixel location (0 is screen bottom)
        if(direction == SwipeDirection.DOWN) swipeEndPoint = yCenter + ( (dimension.getHeight() / 100) * percent );
        else if(direction == SwipeDirection.UP) swipeEndPoint = yCenter - ( (dimension.getHeight() / 100) * percent );
        else throw new IllegalArgumentException("No valid swipe direction specified. Options: SwipeDirection.UP, SwipeDirection.DOWN");

        if(swipeEndPoint > dimension.getHeight() / 2)
        {
            throw new IllegalArgumentException("You are attempting to swipe an area larger than half the size " +
                    "of the screen! This cannot be done as swiping begins from the center (Y Axis).");
        }
        else
        {
            TouchAction touchAction = new TouchAction((AppiumDriver) driver);
            touchAction.tap(xCenter, yCenter).moveTo(xCenter, swipeEndPoint).release().perform();
        }
    }
}
