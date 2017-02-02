package util.factory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.TestEnvironment;
import model.TestdroidEnvironment;
import model.WebEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt-hfc on 11/4/16.
 * This factory will accept a list of JSON environments, and return a List<T extends TestEnvironment> that can be fed into the
 * capabilitiesFactory
 */
public class EnvironmentFactory <T extends TestEnvironment> {

    public List<T> getTestEnvironmentsFromJSON(JsonObject testEnvironments, Class<T> testEnvironmentType)
    {
        Gson gson = new Gson();
        List<T> testEnvironmentList = null;
        for( JsonElement element : testEnvironments.get("capabilities").getAsJsonArray() )
        {
            if(testEnvironmentType == TestdroidEnvironment.class) {
                TestdroidEnvironment testEnvironment = gson.fromJson(element, TestdroidEnvironment.class);
                testEnvironmentList.add((T) testEnvironment);
            }
            else if(testEnvironmentType == WebEnvironment.class) {
                WebEnvironment testEnvironment = gson.fromJson(element, WebEnvironment.class);
                testEnvironmentList.add((T) testEnvironment);
            }
        }
        return testEnvironmentList;
    }
}
