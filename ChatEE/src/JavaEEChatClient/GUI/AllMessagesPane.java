package JavaEEChatClient.GUI;

import JavaEEChatClient.ChatClient;
import JavaEEChatClient.CommonClasses.ChatMessage;
import JavaEEChatClient.NetworkActions.GetMessagesAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by user on 11.08.2015.
 */
public class AllMessagesPane {
    AllMessagesPane() {
        JEditorPane allMessagesPane = new JEditorPane();
        allMessagesPane.setEditable(false);

        try {
            URL url = new URL("https://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html");
            allMessagesPane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(allMessagesPane), BorderLayout.CENTER);
        Runnable getMessages = new GetMessagesAction();

    }
}



