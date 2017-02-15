package model;

/**
 * Created by matt-hfc on 10/31/16.
 * Specific to the Testdroid required capabilities
 */
public class TestObjectEnvironment extends TestEnvironment {

    public String testobject_device_name;
    public String testobject_api_key;
    public String testobject_appium_version;

    public String getTestobject_appium_version() {
        return testobject_appium_version;
    }

    public String getTestobject_device_name() {
        return testobject_device_name;
    }

    public String getTestobject_api_key() {
        return testobject_api_key;
    }
}
