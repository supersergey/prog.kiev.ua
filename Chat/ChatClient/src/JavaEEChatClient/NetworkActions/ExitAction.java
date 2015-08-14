package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.ChatClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by sergey on 12.08.2015.
 */
public class ExitAction {

    public static void doExitGet() {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            URI uri = new URIBuilder(ServerURL.ServerURL+"/exit").addParameter("name", ChatClient.getCurrentUser().getName())
                    .addParameter("room", ChatClient.getCurrentUser().getCurrentRoom()).build();
            HttpDelete httpDelete = new HttpDelete(uri);
            httpClient.execute(httpDelete);
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }


}
