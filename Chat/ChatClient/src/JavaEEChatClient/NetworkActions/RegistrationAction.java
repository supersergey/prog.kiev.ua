package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.GUI.LoginGUI;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by user on 12.08.2015.
 */
public class RegistrationAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String login = LoginGUI.getInstance().getLoginPane().getRegistrationLoginField().getText();
        String passw1 = new String(LoginGUI.getInstance().getLoginPane().getRegistrationPasswordField().getPassword());
        String passw2 = new String(LoginGUI.getInstance().getLoginPane().getRegistrationPassword2Field().getPassword());
        if (!passw1.equals(passw2))
            JOptionPane.showMessageDialog(LoginGUI.getInstance().getMainFrame(),
                    "Passwords do not match, please retry.",
                    "Authentication error",
                    JOptionPane.ERROR_MESSAGE);
        else if (null == login || login.isEmpty() || passw1.isEmpty())
            JOptionPane.showMessageDialog(LoginGUI.getInstance().getMainFrame(),
                    "Invalid login or password, please retry.",
                    "Authentication error",
                    JOptionPane.ERROR_MESSAGE);
        else {
            CloseableHttpClient httpClient = ChatHttpClient.getClient();
            HttpPost request = new HttpPost(ServerURL.ServerURL + "/register");
            Gson loginJson = new Gson();
            String jsonRequest = loginJson.toJson(new LoginData(login, passw1));
            StringEntity entity = new StringEntity(jsonRequest, "UTF-8");
            request.setEntity(entity);
            request.addHeader("content-type", "application/json");

            try {
                org.apache.http.HttpResponse response = httpClient.execute(request);
                if (response.getStatusLine().getStatusCode() == 200)
                    JOptionPane.showMessageDialog(LoginGUI.getInstance().getMainFrame(),
                            "Registration successful, now you can login to chat.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(LoginGUI.getInstance().getMainFrame(),
                            "Registration error. Try to use another login.",
                            "Registration error",
                            JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }
}
