package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.ChatClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by user on 19.08.2015.
 */
public class ChangeChatRoomAction {
    public static void doChangeGet(String newRoom)
    {
        if (null!=newRoom)
            if (!newRoom.isEmpty())
            {
                try {
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                    URI uri = new URIBuilder(ServerURL.ServerURL+"/changeroom").addParameter("name", ChatClient.getCurrentUser().getName())
                            .addParameter("room", newRoom).build();
                    HttpGet getMethod = new HttpGet(uri);
                    CloseableHttpResponse response = httpClient.execute(getMethod);
                    if (response.getStatusLine().getStatusCode()==200)
                    {
                        ChatClient.getCurrentUser().setChatRoom(newRoom);
                    }
                    httpClient.close();
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }

    }
}
