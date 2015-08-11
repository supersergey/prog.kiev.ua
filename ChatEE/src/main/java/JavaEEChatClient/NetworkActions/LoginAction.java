package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.GUI.MainGUI;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

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
         LoginGUI.getInstance().restartLoginGUI();
        else
     {
         LoginGUI.getInstance().shutdownLoginGUI();
         MainGUI.getInstance().createGUI();
     }
    }

    private boolean doLoginPost() {
        login = LoginGUI.getInstance().getLoginPane().getLoginField().getText();
        password = new String(LoginGUI.getInstance().getLoginPane().getPasswordField().getPassword());
        if (null == login || null == password || login.isEmpty() || password.isEmpty())
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
            return response.getStatusLine().getStatusCode() == 201;
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        return true;
    }

    class LoginData {
        String login;
        String password;

        public LoginData(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
