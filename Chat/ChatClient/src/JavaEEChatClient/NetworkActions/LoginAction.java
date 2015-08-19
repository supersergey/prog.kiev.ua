package JavaEEChatClient.NetworkActions;

import JSON.LoginJSON;
import JavaEEChatClient.ChatClient;
import JavaEEChatClient.CommonClasses.User;
import JavaEEChatClient.GUI.LoginGUI;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
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
         ChatClient.setCurrentUser(new User(login, "default"));
     }
    }

    private boolean doLoginPost() {
        login = LoginGUI.getInstance().getLoginPane().getLoginField().getText();
        password = new String(LoginGUI.getInstance().getLoginPane().getPasswordField().getPassword());
        if (null == login || login.isEmpty() || password.isEmpty())
            return false;
        else try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(ServerURL.ServerURL+"/login");
            Gson gson = new Gson();
            String requestJSON = gson.toJson(new LoginJSON(login, password));
            StringEntity entity = new StringEntity(requestJSON, "UTF-8");
            request.setEntity(entity);
            request.addHeader("content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(request);
            httpClient.close();
            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            return statusCode == 200;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
