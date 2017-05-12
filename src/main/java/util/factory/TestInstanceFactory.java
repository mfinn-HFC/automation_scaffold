package util.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.TestObjectEnvironment;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.reflections.Reflections;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

/**
 * Created by matt-hfc on 12/14/16.
 */
public class TestInstanceFactory {

    protected static String testServer;
    protected static String appName;
    protected static String testDirectory;
    protected static boolean remote;
    protected static String jsonLocation;
    protected static Class baseClazz;

    private static EnvironmentFactory environmentFactory = new EnvironmentFactory();

    public TestInstanceFactory(String jsonLocation, Class baseClazz) {
        this.jsonLocation = jsonLocation;
        this.baseClazz = baseClazz;
    }

    /**
     * Since we need the driver to be set BEFORE @Test runs, so that we can use the TestObject listener to update
     * the test status, we have to create all test instances via factory, and feed the driver into the test constructor
     * @param
     * @return
     */

    @Factory(dataProvider = "capabilities")
    public static Object[] createInstances(DesiredCapabilities capabilities) throws Exception
    {
        Reflections reflections = new Reflections(testDirectory);
        Set<Class<?>> packageClasses  = reflections.getSubTypesOf(baseClazz);
        Object[] instantiatedTests = new Object[packageClasses.size()];

        int index = 0;
        for (Class clazz : packageClasses)
        {
            try
            {
                Constructor constructor = clazz.getConstructor(DesiredCapabilities.class);
                instantiatedTests[index] = constructor.newInstance(capabilities);
                index++;
            }
            catch(Exception e)
            {
                System.out.println("***Constructor / Init Exception for: " + clazz.getSimpleName() + "***");
                System.out.println( e.getMessage() );
            }
        }
        return instantiatedTests;
    }

    @DataProvider(name = "capabilities")
    public static Object[][] setEnvironments() throws FileNotFoundException, IllegalAccessException, NoSuchFieldException, MalformedURLException
    {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader("/Users/matt-hfc/IdeaProjects/vdb-android-automation/src/test/resources/androidEnvironments.json"));

        // Set basic values for instantiating tests in the @Factory
        testServer = jsonElement.getAsJsonObject().get("testServer").getAsString();
        appName = jsonElement.getAsJsonObject().get("appName").getAsString();
        testDirectory = jsonElement.getAsJsonObject().get("testDirectory").getAsString();

        try
        {
            int index = 0;
            if (jsonElement.getAsJsonObject().get("remote").getAsString().contains("true"))
            {
                remote = true;
                try
                {
                    List<TestObjectEnvironment> testEnvironmentList =
                            (List<TestObjectEnvironment>) environmentFactory.getTestEnvironmentsFromJSON(jsonElement.getAsJsonObject(), TestObjectEnvironment.class);
                    Object[][] array = new Object[testEnvironmentList.size()][1];
                    for(TestObjectEnvironment testEnvironment : testEnvironmentList)
                    {
                        DesiredCapabilities capabilities = new CapabilitiesFactory<>().getCapabilities(testEnvironment);
                        array[index][0] = capabilities;
                        index++;
                    }
                    return array;
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                System.out.println("Remote/Cloud testing not enabled! Local testing not yet supported by this factory");
            }
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
        }
        System.out.println("**ERROR: Did not create a driver, returning null, test run will be ignored!");
        return null;
    }
}
