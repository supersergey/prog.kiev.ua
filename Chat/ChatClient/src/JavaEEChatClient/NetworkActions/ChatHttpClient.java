package JavaEEChatClient.NetworkActions;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by sergey on 14.08.2015.
 */
public class ChatHttpClient {
    private static ChatHttpClient ourInstance = new ChatHttpClient();
    private static MultiThreadedHttpConnectionManager connectionManager =
            new MultiThreadedHttpConnectionManager();
    private static HttpClient client = new HttpClient(connectionManager);

    public static ChatHttpClient getInstance() {
        return ourInstance;
    }

    private ChatHttpClient() {
    }

    public static HttpClient getClient() {
        return client;
    }
}
