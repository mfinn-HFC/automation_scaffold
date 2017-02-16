package util;

import com.google.gson.*;
import com.sun.mail.util.BASE64EncoderStream;
import enums.DeviceType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.netty.handler.codec.base64.Base64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This class will tell you what devices are free to launch test instances
 */
public final class TestObjectDeviceClient {

    private final static String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private final static String baseURL = "https://app.testobject.com:443/api/rest/devices/v1/";
    private final static String availableDevicesURI = "devices/available";
    private final static String iosIdentifierString = "iP";
    // Max 10 minute wait with these values
    private final static int maxLoops = 100;
    private final static int waitInterval = 6000;
    private int loopCount = 0;
    public static String currentDevice;

    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(1);

    public TestObjectDeviceClient() {}

    public JsonArray getAvailableFreeDevices(DeviceType deviceType)
    {

        JsonArray devices;
        JsonArray finalDevices = new JsonArray();

        try
        {
            HttpClient httpClient = HttpClientBuilder.create().setUserAgent(userAgent).build();

            HttpGet httpGet = new HttpGet(baseURL + availableDevicesURI);
            httpGet.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                devices = convertEntityToJson(response.getEntity());
                finalDevices = filterFreeDevicesByDeviceType(deviceType, devices);
                return finalDevices;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return finalDevices;
    }

    public JsonArray filterFreeDevicesByDeviceType(DeviceType deviceType, JsonArray devices)
    {
        JsonArray finalDevices = new JsonArray();

        for(int i = 0; i < devices.size(); i++)
        {
            if (devices.get(i).getAsString().contains("free"))
            {
                if (deviceType == DeviceType.ANDROID)
                {
                    if (!devices.get(i).getAsString().contains(iosIdentifierString))
                        finalDevices.add(devices.get(i));
                }
                else if (deviceType == DeviceType.IOS)
                {
                    if (devices.get(i).getAsString().contains(iosIdentifierString))
                        finalDevices.add(devices.get(i));
                }
            }
        }
        return finalDevices;
    }

    public JsonArray convertEntityToJson(HttpEntity entity)
    {
        JsonArray devicesArray = new JsonArray();
        try
        {
            String results = EntityUtils.toString(entity);
            JsonParser parser = new JsonParser();

            JsonElement deviceNamesElement = parser.parse(results);
            devicesArray = deviceNamesElement.getAsJsonArray();
        }
        catch (IOException e) {}
        return devicesArray;
    }

    public void releaseCurrentDevice()
    {
        currentDevice = null;
    }

    public String waitForDeviceAvailability(DeviceType deviceType)
    {
        JsonArray devices = getAvailableFreeDevices(deviceType);

        // If the method was previously called and maxed out, start at 0
        if(loopCount == maxLoops) loopCount = 0;
        try
        {
            while(loopCount < maxLoops)
            {
                if (devices.size() == 0 || devices == null) {
                    loopCount++;
                    Thread.sleep(waitInterval);
                    devices = getAvailableFreeDevices(deviceType);
                }
                else break;
            }

            if(devices.size() >= 1 && queue.isEmpty() && currentDevice == null)
            {
                currentDevice = devices.get(0).getAsString();
                queue.add(currentDevice);
            }
            return queue.take();
        }
        catch (InterruptedException e) {}
        System.out.println("***EPIC FAILURE: Nothing in device blocking queue! No device for testing - return null!***");
        return null;
    }
}