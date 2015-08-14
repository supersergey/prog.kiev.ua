package JavaEEChatClient.NetworkActions;

import JSON.MessageJSON;
import JSON.MessagesJSON;
import JavaEEChatClient.ChatClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
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
        try {
            byte[] jsonRequest = json.toJson(messageJSON, MessageJSON.class).getBytes("UTF-8");

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(ServerURL.ServerURL + "/postmessage");
            HttpEntity entity = new ByteArrayEntity(jsonRequest);
            request.setEntity(entity);
            request.addHeader("content-type", "application/json");
            httpClient.execute(request);
            httpClient.close();
            int a = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
