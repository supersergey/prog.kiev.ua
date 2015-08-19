package JavaEEChatClient.NetworkActions;

import JSON.MessageJSON;
import JSON.MessagesJSON;
import JavaEEChatClient.ChatClient;
import JavaEEChatClient.GUI.MainGUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by user on 12.08.2015.
 */
public class GetMessagesAction implements Runnable {

    private Thread thread;
    private HttpGet getMethod;

    public GetMessagesAction() {

        thread = new Thread(this, "All messages reader thread");
        thread.start();
    }

    @Override
    public void run() {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        Gson gson = new GsonBuilder().create();

        while (!Thread.interrupted()) {
            try {

                URI uri = new URIBuilder(ServerURL.ServerURL + "/getmessages").addParameter("user", ChatClient.getCurrentUser().getName()).addParameter("room", ChatClient.getCurrentUser().getChatRoom()).build();
                getMethod = new HttpGet(uri);

                CloseableHttpResponse response = httpClient.execute(getMethod);
                if (null != response) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        byte buf[] = new byte[(int) response.getEntity().getContentLength()];
                        int readCount = response.getEntity().getContent().read(buf);
                        if (readCount != -1) {
                            String JSONresponse = new String(buf, "UTF-8");
                            MessagesJSON messagesJSON = gson.fromJson(JSONresponse, MessagesJSON.class);
                            StringBuilder sb = new StringBuilder();
                            for (MessageJSON m : messagesJSON.getMessages()) {
                                sb.append(m.getName()).append(" ");
                                sb.append(m.getDate()).append(" ");
                                sb.append(m.getBody()).append("\r\n");
                            }
                            MainGUI.getInstance().getAllMessagesPane().getAllMessagesPane().setText(sb.toString());
                        }
                    }
                    response.close();
                }
                Thread.sleep(3000);
            } catch (IOException | URISyntaxException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            httpClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}