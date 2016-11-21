package util.factory;

import model.TestEnvironment;
import org.aspectj.weaver.ast.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt-hfc on 10/31/16.
 */
public class CapabilitiesFactory {

    public List<DesiredCapabilities> getCapabilities(List<TestEnvironment> testEnvironments) throws IllegalAccessException, NoSuchFieldException {
        List<DesiredCapabilities> desiredCapabilitiesList = new ArrayList<>();
        for(TestEnvironment testEnvironment : testEnvironments)
        {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            for (Field field : testEnvironment.getClass().getDeclaredFields())
            {
                try {


                    desiredCapabilities.setCapability(field.getName(), testEnvironment.getClass().getField(field.getName()).get(testEnvironment));
                } catch (Exception e) {}
            }
            desiredCapabilitiesList.add(desiredCapabilities);
        }
        return desiredCapabilitiesList;
    }

}
