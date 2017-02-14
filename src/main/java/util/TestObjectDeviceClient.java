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

/**
 * This class will tell you what devices are free to launch test instances
 */
public final class TestObjectDeviceClient {

    private final static String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private final static String baseURL = "https://app.testobject.com:443/api/rest/devices/v1/";
    private final static String availableDevicesURI = "devices/available";
    private final static String iosIdentifierString = "iP";
    // Max 5 minute wait with these values
    private final static int maxLoops = 50;
    private final static int waitInterval = 6000;
    private static int loopCount = 0;

    private TestObjectDeviceClient() {}

    public static JsonArray getAvailableFreeDevices(DeviceType deviceType, String apiKey)
    {

        JsonArray devices = new JsonArray();

        try
        {
            /*CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            Credentials credentials = new UsernamePasswordCredentials(apiKey, "");
            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();*/
            HttpClient httpClient = HttpClientBuilder.create().setUserAgent(userAgent).build();

            HttpGet httpGet = new HttpGet(baseURL + availableDevicesURI);
            httpGet.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                devices = convertEntityToJson(response.getEntity());
                for(int i = 0; i < devices.size(); i++)
                {
                    if(!devices.get(i).getAsString().contains("free")) devices.remove(i);

                    if(deviceType == DeviceType.ANDROID)
                    {
                        if (devices.get(i).getAsString().contains(iosIdentifierString))
                            devices.remove(i);
                    }
                    if(deviceType == DeviceType.IOS)
                    {
                        if(!devices.get(i).getAsString().contains(iosIdentifierString))
                            devices.remove(i);
                    }
                }
                return devices;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return devices;
    }

    public static JsonArray convertEntityToJson(HttpEntity entity)
    {
        JsonArray resultElement = new JsonArray();
        try
        {
            String results = EntityUtils.toString(entity);
            JsonParser parser = new JsonParser();

            JsonElement jsonElement = parser.parse(results);
            resultElement = jsonElement.getAsJsonArray();
            System.out.println(resultElement);
        }
        catch (IOException e) {}
        return resultElement;
    }

    public static String waitForDeviceAvailability(DeviceType deviceType, String apiKey)
    {
        JsonArray devices = getAvailableFreeDevices(deviceType, apiKey);

        // If the method was previously called and maxed out, start at 0
        if(loopCount == maxLoops) loopCount = 0;
        try
        {
            while(loopCount < maxLoops) {
                if (devices.size() == 0 || devices == null) {
                    loopCount++;
                    Thread.sleep(waitInterval);
                    devices = getAvailableFreeDevices(deviceType, apiKey);
                }
            }
        }
        catch (InterruptedException e) {}
        return devices.get(0).getAsString();
    }
}