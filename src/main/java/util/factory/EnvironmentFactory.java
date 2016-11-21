package util.factory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.TestEnvironment;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by matt-hfc on 11/4/16.
 * This factory will accept a list of JSON environments, and return a List<TestEnvironment> that can be fed into the
 * capabilitiesFactory
 */
public class EnvironmentFactory {

    private String testServer;

    public EnvironmentFactory(String testServer)
    {
        this.testServer = testServer;
    }

    public List<TestEnvironment> getTestEnvironmentsFromJSON(JsonObject testEnvironments)
    {
        Gson gson = new Gson();
        List<TestEnvironment> testEnvironmentList = null;
        for( JsonElement element : testEnvironments.get("environments").getAsJsonArray() )
        {
            TestEnvironment testEnvironment = gson.fromJson(element, TestEnvironment.class);
            testEnvironmentList.add(testEnvironment);
        }
        return testEnvironmentList;
    }
}
