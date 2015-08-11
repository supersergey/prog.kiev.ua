package JavaEEChatClient.NetworkActions;

import JavaEEChatClient.ChatConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    @Override
    public void actionPerformed(ActionEvent e) {

        try
        {
            URL url = new URL("localhost:8080/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            Gson loginJson = new Gson();
            String request = loginJson.toJson(new LoginData(login, password));

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            OutputStream os = http.getOutputStream();
            os.write(request.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader( http.getInputStream(),"utf-8"));
            String[] reply = br.readLine().split(" ");
            if (!reply[1].equals("200"))
                System.out.println("Bad login");
            else
                System.out.println("Good login");
        }
        catch (IOException ignored)
        {
            ignored.printStackTrace();
        }



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
