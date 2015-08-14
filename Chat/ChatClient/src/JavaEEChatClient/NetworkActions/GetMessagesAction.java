package JavaEEChatClient.NetworkActions;

import JSON.MessageJSON;
import JSON.MessagesJSON;
import JavaEEChatClient.ChatClient;
import JavaEEChatClient.CommonClasses.ChatMessage;
import JavaEEChatClient.GUI.MainGUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
        HttpClient httpClient = ChatHttpClient.getClient();

        Gson responseJson = new GsonBuilder().create();
        try {
            // URI uri = new URIBuilder(ServerURL.ServerURL + "/getmessages").addParameter("user", ChatClient.getCurrentUser().getName()).addParameter("room", "default").build();
            String uri = "http://localhost:8080/login";

            GetMethod get = new GetMethod(uri);

            while (!Thread.interrupted()) {
                    HttpResponse response = httpClient.executeMethod(get);

                    /*if (response.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();

                        byte[] buf = new byte[(int) entity.getContentLength()];
                        entity.getContent().read(buf);
                        String JSONresponse = new String(buf, "UTF-8");

                        MessagesJSON messagesJSON = responseJson.fromJson(JSONresponse, MessagesJSON.class);
                        StringBuilder sb = new StringBuilder();
                        for (MessageJSON m : messagesJSON.getMessages()) {
                            sb.append(m.getName()).append(" ");
                            sb.append(m.getDate()).append(" ");
                            sb.append(m.getBody()).append("\r\n");
                        }
                        MainGUI.getInstance().getAllMessagesPane().getAllMessagesPane().setText(sb.toString());
                    }*/
                Thread.sleep(3000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}