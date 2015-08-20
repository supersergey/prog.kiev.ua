package JavaEEChatClient.NetworkActions;

import JSON.RoomJSON;
import JSON.RoomsJSON;
import JavaEEChatClient.ChatClient;
import JavaEEChatClient.GUI.ChatRoomsPane;
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
import java.util.List;

/**
 * Created by sergey on 19.08.2015.
 */
public class GetRoomsListAction implements Runnable {
    private Thread thread;

    public GetRoomsListAction() {
        thread = new Thread(this, "Get rooms list");
        // thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        while (!Thread.interrupted())
        {
            try
            {
                URI uri = new URIBuilder(ServerURL.ServerURL + "/rooms").addParameter("user", ChatClient.getCurrentUser().getName()).addParameter("room", ChatClient.getCurrentUser().getChatRoom()).build();
                HttpGet httpGet = new HttpGet(uri);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode()==200)
                {
                    RoomsJSON roomsJSON = new RoomsJSON();
                    Gson gson = new GsonBuilder().create();
                    byte buf[] = new byte[(int) response.getEntity().getContentLength()];
                    int readcount = response.getEntity().getContent().read(buf);
                    if (readcount>0)
                    {
                        String responseJSON = new String(buf, "UTF-8");
                        roomsJSON = gson.fromJson(responseJSON, RoomsJSON.class);
                    }
                    StringBuilder sb = new StringBuilder();
                    for (RoomJSON room : roomsJSON.getRooms())
                    {
                        sb.append(room.getRoomName()).append("\r\n");
                    }
                    MainGUI.getInstance().getChatRoomsPane().getjTextArea().setText(sb.toString());
                    Thread.sleep(5000);
                }
                response.close();
            }
            catch (URISyntaxException | IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            httpClient.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
