package JavaEEChatClient.NetworkActions;

import JSON.UserJSON;
import JSON.UsersJSON;
import JavaEEChatClient.GUI.MainGUI;
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
        CloseableHttpClient httpClient = ChatHttpClient.getClient();

        try {
            URI uri = new URIBuilder(ServerURL.ServerURL + "/getonlineusers").build();
            while (!Thread.interrupted()) {

/*                HttpGet httpGet = new HttpGet(uri);
                StringBuilder sb = new StringBuilder();
                HttpResponse response = httpClient.execute(httpGet);
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                while (br.ready())
                    sb.append(br.readLine());
                UsersJSON usersJSON = gson.fromJson(sb.toString(), UsersJSON.class);
                MainGUI.getInstance().getUsersOnlinePane().clearUsers();
                for (UserJSON userJSON : usersJSON.getUsers()) {
                    String username = userJSON.getName();
                    MainGUI.getInstance().getUsersOnlinePane().addUser(username);
                }*/
                Thread.sleep(3000);
            }
        } // catch (IOException | InterruptedException | URISyntaxException ex) {
        catch (Exception ex) {
        ex.printStackTrace();
        }
    }
}
