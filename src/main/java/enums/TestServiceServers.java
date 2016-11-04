package enums;

/**
 * Created by matt-hfc on 10/31/16.
 */
public enum TestServiceServers {

    // This is set up to point to the HFC saucelabs account
    SAUCE_LABS("http://hfc-automation:7c5331f5-c08e-401f-8609-a493bd59897c@ondemand.saucelabs.com:80/wd/hub");

    String URL;

    TestServiceServers(String URL) { this.URL = URL; }

    public String getURL() {
        return URL;
    }
}
