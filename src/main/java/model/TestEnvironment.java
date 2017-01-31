package model;

/**
 * Created by matt-hfc on 10/31/16.
 * Base class for types of test environments - saucelabs, testdroid, etc
 */
public class TestEnvironment {

    public String testServer; // Cloud service you want to point at

    public String getTestServer() {
        return testServer;
    }

    public void setTestServer(String testServer) {
        this.testServer = testServer;
    }
}
