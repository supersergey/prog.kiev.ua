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
import javax.swing.text.DefaultCaret;
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
    private JEditorPane allMessagesPane = new JEditorPane();
    AllMessagesPane() {

        allMessagesPane.setEditable(false);
        DefaultCaret caret = (DefaultCaret)allMessagesPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(allMessagesPane), BorderLayout.CENTER);
        Runnable getMessages = new GetMessagesAction();
    }

    public JEditorPane getAllMessagesPane() {
        return allMessagesPane;
    }
}



