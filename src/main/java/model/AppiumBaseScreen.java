package model;

import enums.SwipeDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by matt-hfc on 2/7/17.
 * A base object for all screens / page objects to inherit from, which will contain generic methods for
 * Appium
 */
public class AppiumBaseScreen<T extends AppiumDriver> {

    private T driver;
    private final int waitTime = 1000;

    public AppiumBaseScreen(T driver) {
        this.driver = driver;

    }

    // Swipe a mobile screen by a percentage of the screen size, UP or DOWN
    public void swipeByPercent(int percent, SwipeDirection direction)
    {
        Dimension dimension = driver.manage().window().getSize();
        System.out.println("Size of device: " + dimension.getWidth() + " by " + dimension.getHeight());
        int xCenter = (dimension.getWidth() / 2);
        int yCenter = (dimension.getHeight() / 2);
        int swipeEndPoint;

        // For Appium, down means higher pixel location, up means lower pixel location (0 is screen bottom)
        if(direction == SwipeDirection.DOWN) swipeEndPoint = ( (dimension.getHeight() / 100) * percent );
        else if(direction == SwipeDirection.UP) swipeEndPoint = ( (dimension.getHeight() / 100) * percent );
        else throw new IllegalArgumentException("No valid swipe direction specified. Options: SwipeDirection.UP, SwipeDirection.DOWN");

        if( (swipeEndPoint > (yCenter * 2) ) || swipeEndPoint < 0)
        {
            throw new IllegalArgumentException("You are attempting to swipe an area larger than half the size " +
                    "of the screen! This cannot be done as swiping begins from the center (Y Axis). swipeEndPoint value: " + swipeEndPoint);
        }
        else
        {
            System.out.println("Attempting to swipe to location: " + swipeEndPoint +
                    " - from location X: " + xCenter + " from location Y: " + yCenter);
            TouchAction touchAction = new TouchAction(driver);
            touchAction.press(xCenter, yCenter).moveTo(0, -swipeEndPoint).release().perform();
        }
    }
}
