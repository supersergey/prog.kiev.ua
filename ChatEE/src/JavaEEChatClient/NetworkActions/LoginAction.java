package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.ChatClient;
import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.User;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by user on 11.08.2015.
 */
public class LoginAction implements ActionListener {
    private String login;
    private String password;

    public LoginAction(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void actionPerformed(ActionEvent e) {
     if (!doLoginPost())
     {
         JOptionPane.showMessageDialog(LoginGUI.getInstance().getMainFrame(),
                 "Incorrect login, please retry.",
                 "Authentication error",
                 JOptionPane.ERROR_MESSAGE);
     }
        else
     {
         LoginGUI.getInstance().shutdownLoginGUI();
         ChatClient.runMainGUI();
         ChatClient.setCurrentUser(new User(login));
     }
    }

    private boolean doLoginPost() {
        login = LoginGUI.getInstance().getLoginPane().getLoginField().getText();
        password = new String(LoginGUI.getInstance().getLoginPane().getPasswordField().getPassword());
        if (null == login || login.isEmpty() || password.isEmpty())
            return false;
        else try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://localhost:8080/login");
            Gson loginJson = new Gson();
            String jsonRequest = loginJson.toJson(new LoginData(login, password));
            StringEntity entity = new StringEntity(jsonRequest, "UTF-8");
            request.setEntity(entity);
            request.addHeader("content-type", "application/json");

            org.apache.http.HttpResponse response = httpClient.execute(request);
            httpClient.close();
            return response.getStatusLine().getStatusCode() == 200;
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        return true;
    }
}
