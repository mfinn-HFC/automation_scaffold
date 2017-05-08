package model;

/**
 * Created by matt-hfc on 10/31/16.
 * Base class for types of test environments - saucelabs, testdroid, etc
 */
public class TestEnvironment {

    public String testServer; // Cloud service you want to point at
    public String appName; // Name of the app for telling the test service which project you're testing

    public String getTestServer() {
        return testServer;
    }

    public void setTestServer(String testServer) {
        this.testServer = testServer;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}