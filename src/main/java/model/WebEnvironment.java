package model;

import org.aspectj.weaver.ast.Test;

/**
 * Created by matt-hfc on 10/31/16.
 * Capabilities for Saucelabs
 */
public class WebEnvironment extends TestEnvironment {

    public String platform;
    public String platformName;
    public String version;
    public String browserName; // Web tests only
    public String name; // Not sure if we're going to use this one, we will need to set the name somewhere

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

}
