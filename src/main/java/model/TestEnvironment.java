package model;

/**
 * Created by matt-hfc on 10/31/16.
 * This will describe a test environment that we want the test to run in, to help determine what VM or device the
 * cloud service we are testing in will need.
 */
public class TestEnvironment {

    private String platform;
    private String version;
    private String browser; // Web tests only
    private String name; // Not sure if we're going to use this one, we will need to set the name somewhere
    private String testServer; // Cloud service you want to point at

    public String getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getBrowser() {
        return browser;
    }

    public String getTestServer() {
        return testServer;
    }
}
