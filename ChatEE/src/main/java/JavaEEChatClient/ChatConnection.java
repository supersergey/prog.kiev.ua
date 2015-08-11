package JavaEEChatClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 11.08.2015.
 */
public class ChatConnection {
    private URL url;
    private static HttpURLConnection http;

    public ChatConnection(String urlString) throws IOException {
        this.url = new URL(urlString);
        http = (HttpURLConnection) url.openConnection();
    }

    public static HttpURLConnection getHttp() {
        return http;
    }
}
