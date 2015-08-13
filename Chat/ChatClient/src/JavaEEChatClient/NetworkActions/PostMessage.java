package JavaEEChatClient.NetworkActions;

import JSON.MessageJSON;
import JSON.MessagesJSON;
import JavaEEChatClient.ChatClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 13.08.2015.
 */
public class PostMessage {

    public static void doPost(String postMessage) {
        MessagesJSON messagesJSON = new MessagesJSON();
        MessageJSON messageJSON = new MessageJSON();
        messageJSON.setBody(postMessage);
        messageJSON.setName(ChatClient.getCurrentUser().getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
        messageJSON.setDate(sdf.format(new Date()));
        messagesJSON.getMessages().add(messageJSON);
        Gson json = new GsonBuilder().create();
        String jsonRequest = json.toJson(messageJSON, MessageJSON.class);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(ServerURL.ServerURL+"/postmessage");
        StringEntity entity = new StringEntity(jsonRequest, "UTF-8");
        entity.setContentEncoding("UTF-8");
        request.setEntity(entity);
        request.addHeader("content-type", "application/json");

        try {
            org.apache.http.HttpResponse response = httpClient.execute(request);
            httpClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
