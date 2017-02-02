package util.factory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.TestEnvironment;
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
        List<T> testEnvironmentList = new ArrayList<>();
        for( JsonElement element : testEnvironments.get("capabilities").getAsJsonArray() )
        {
            T testEnvironment = testEnvironmentType.cast(gson.fromJson(element, testEnvironmentType));
            testEnvironmentList.add(testEnvironment);
        }
        return testEnvironmentList;
    }
}
