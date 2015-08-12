package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.GUI.MainGUI;
import JavaEEChatClient.GUI.UsersOnlinePane;
import JavaEEChatClient.NetworkActions.JSONDataStructures.UserJSON;
import JavaEEChatClient.NetworkActions.JSONDataStructures.UsersJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by sergey on 13.08.2015.
 */
public class GetOnlineUsersAction implements Runnable {
    Thread thread;

    public GetOnlineUsersAction() {

        thread = new Thread(this, "Get the list of online users");
        thread.start();
    }

    @Override
    public void run() {
        Gson gson = new GsonBuilder().create();

        while (!thread.interrupted())
        {
            try
            {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                URI uri = new URIBuilder("http://localhost:8080/getonlineusers").build();
                HttpGet httpGet = new HttpGet(uri);
                HttpResponse response = httpClient.execute(httpGet);
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while (br.ready())
                    sb.append(br.readLine());
                UsersJSON usersJSON = gson.fromJson(sb.toString(), UsersJSON.class);
                MainGUI.getInstance().getUsersOnlinePane().clearUsers();
                for (UserJSON userJSON : usersJSON.getUsers())
                {
                    String username = userJSON.getName();
                    MainGUI.getInstance().getUsersOnlinePane().addUser(username);
                }
                Thread.sleep(3000);
            }
            catch (IOException | InterruptedException | URISyntaxException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
