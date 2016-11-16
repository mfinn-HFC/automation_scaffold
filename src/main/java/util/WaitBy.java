package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Custom waits to ensure interactions don't break
 */
public class WaitBy {

    private WebDriver driver;
    private int implicitWaitTime = 5;
    private int timeout = 120000;
    private int polling = 5500;
    private FluentWait wait;

    public WaitBy(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(timeout, TimeUnit.MILLISECONDS)
                .pollingEvery(polling, TimeUnit.MILLISECONDS);
        this.driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
    }

    /**
     * Would this be better served by some kind of Lambda? Couldn't figure out a working lambda for this stuff
     */
    /*public void waitByWebDriverMethod(final By locator, Function webdriverMethod) {
        final long startTime = System.currentTimeMillis();
        wait.until(new ExpectedCondition() {
            @Override
            public Object apply( WebDriver webDriver ) {
                try {
                    webdriverMethod.apply(webDriver.findElement(locator));
                    return true;
                }
                catch (Exception e) {
                    return false;
                }
            }
        });
    }*/

    public void waitByLocatorElementNotDisplayed(final By locator) {
        final long startTime = System.currentTimeMillis();
        wait.until( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply( WebDriver webDriver ) {
                try {
                    webDriver.findElement(locator);
                    return false;
                } catch (WebDriverException e) {
                    return true;
                }
            }
        } );
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void waitByLocator(final By locator) {
        final long startTime = System.currentTimeMillis();
        wait.until( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply( WebDriver webDriver ) {
                try {
                    webDriver.findElement(locator);
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }
        } );
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void clickByLocator(final By locator) {
        final long startTime = System.currentTimeMillis();
        wait.until( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply( WebDriver webDriver ) {
                try {
                    webDriver.findElement(locator).click();
                    return true;
                } catch (WebDriverException e) {
                    return false;
                }
            }
        } );
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void selectByLocator(final By locator, final String value) {
        final long startTime = System.currentTimeMillis();
        wait.until( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    new Select(driver.findElement(locator)).selectByValue(value);
                    // If the drop down isn't selected to the specified value, try again
                    if(!driver.findElement(locator).getAttribute("value").equals(value)) return false;
                    return true;
                } catch ( WebDriverException e ) {
                    return false;
                }
            }
        } );
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }

    public void sendKeysByLocator(final By locator, final String value) {
        final long startTime = System.currentTimeMillis();
        wait.until( new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    driver.findElement(locator).sendKeys(value);
                    // If the text value is not in the field, try it again
                    if(!driver.findElement(locator).getAttribute("value").equals(value)) return false;
                    return true;
                } catch ( WebDriverException e ) {
                    driver.findElement(locator).clear();
                    return false;
                }
            }
        } );
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
    }
}
