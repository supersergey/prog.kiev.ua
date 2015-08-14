package JavaEEChatClient.NetworkActions;

import JSON.UserJSON;
import JSON.UsersJSON;
import JavaEEChatClient.GUI.MainGUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by sergey on 13.08.2015.
 */
public class GetOnlineUsersAction implements Runnable {
    private Thread thread;
    private HttpGet getMethod;

    public GetOnlineUsersAction() {

        thread = new Thread(this, "Get the list of online users");
        try {
            URI uri = new URIBuilder(ServerURL.ServerURL + "/getonlineusers").build();
            getMethod = new HttpGet(uri);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } finally {
            thread.start();
        }
    }

    @Override
    public void run() {
        Gson gson = new GsonBuilder().create();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        while (!Thread.interrupted()) {
            try {
                CloseableHttpResponse response = httpClient.execute(getMethod);
                if (null != response) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        byte[] buf = new byte[(int) response.getEntity().getContentLength()];
                        int readcount = response.getEntity().getContent().read(buf);
                        String JSONresponse = new String(buf, "UTF-8");
                        if (readcount != -1) {
                            UsersJSON usersJSON = gson.fromJson(JSONresponse, UsersJSON.class);
                            MainGUI.getInstance().getUsersOnlinePane().clearUsers();
                            for (UserJSON userJSON : usersJSON.getUsers()) {
                                String username = userJSON.getName();
                                MainGUI.getInstance().getUsersOnlinePane().addUser(username);
                            }
                        }
                    }
                    response.close();
                }
                Thread.sleep(3000);
            } catch (IOException | InterruptedException ex) {
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
