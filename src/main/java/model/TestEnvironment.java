package model;

/**
 * Created by matt-hfc on 10/31/16.
 * This will describe a test environment that we want the test to run in, to help determine what VM or device the
 * cloud service we are testing in will need.
 */
public class TestEnvironment {

    public String platform;
    public String version;
    public String browserName; // Web tests only
    public String name; // Not sure if we're going to use this one, we will need to set the name somewhere
    public String testServer; // Cloud service you want to point at

    public String getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getTestServer() {
        return testServer;
    }
}
