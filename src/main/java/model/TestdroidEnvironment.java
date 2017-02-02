package model;

/**
 * Created by matt-hfc on 10/31/16.
 * Specific to the Testdroid required capabilitiesds
 */
public class TestdroidEnvironment extends TestEnvironment {

    public String testdroid_app;
    public String testdroid_apiKey;
    public String testdroid_project;
    public String testdroid_testrun;
    public String testdroid_device;
    public String testdroid_target;
    public String deviceName;

    public String getTestdroid_target() {
        return testdroid_target;
    }

    public String getTestdroid_app() {
        return testdroid_app;
    }

    public String getTestdroid_apiKey() {
        return testdroid_apiKey;
    }

    public String getTestdroid_testrun() {
        return testdroid_testrun;
    }

    public String getTestdroid_project() {
        return testdroid_project;
    }

    public String getTestdroid_device() {
        return testdroid_device;
    }
}
