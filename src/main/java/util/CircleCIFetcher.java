package util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by matt-hfc on 12/15/16.
 * This class will be used to fetch artifacts (mobile builds) from CircleCI
 */
public class CircleCIFetcher {

    // This key is linked to mfinn@happyfuncorp.com in CircleCI
    // TODO need a default email like qa@happyfuncorp.com to hold the API Key & hide key in a resources file
    private static final String key = "fb5572b29c9045f6d264047922123d1e9da7fd84";
    private String projectURI = null;
    private CloseableHttpClient client = HttpClients.createDefault();
    private String email = "";
    private String password = "";

    public CircleCIFetcher(String projectURI)
    {
        this.projectURI = projectURI;
    }

    private void authenticate()
    {

    }
}