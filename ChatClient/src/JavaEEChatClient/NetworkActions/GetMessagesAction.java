package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.ChatClient;
import JavaEEChatClient.CommonClasses.ChatMessage;
import JavaEEChatClient.GUI.MainGUI;
import JavaEEChatClient.NetworkActions.JSONDataStructures.MessageJSON;
import JavaEEChatClient.NetworkActions.JSONDataStructures.MessagesJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.StringBuilder;
import java.lang.Thread;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by user on 12.08.2015.
 */
public class GetMessagesAction implements Runnable {

    private Thread thread;

    public GetMessagesAction() {

        thread = new Thread(this, "All messages reader thread");
        thread.start();
    }

    @Override
    public void run() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            Gson responseJson = new GsonBuilder().create();

            while (!thread.interrupted()) {
                try {
                    URI uri = new URIBuilder("http://localhost:8080/getmessages").addParameter("user", ChatClient.getCurrentUser().getName()).addParameter("room", "default").build();
                    HttpGet request = new HttpGet(uri);
                    HttpResponse response = httpClient.execute(request);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                        StringBuilder sb = new StringBuilder();
                        while (reader.ready()) {
                            sb.append(reader.readLine());
                        }
                        MessagesJSON messagesJSON = responseJson.fromJson(sb.toString(), MessagesJSON.class);
                        sb = new StringBuilder();
                        for (MessageJSON m : messagesJSON.getMessages())
                        {
                            sb.append(m.getName()).append(" ");
                            sb.append(m.getDate()).append(" ");
                            sb.append(m.getBody()).append("\r\n");
                        }
                        MainGUI.getInstance().getAllMessagesPane().getAllMessagesPane().setText(sb.toString());
                    }
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            httpClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    class ChatMessages
    {
        ChatMessage[] messages;
    }
}
