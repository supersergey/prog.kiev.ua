package ChatProject.ChatServerPackage.ServletPackage;

import JSON.LoginJSON;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by user on 12.08.2015.
 */
public class CommonOperations {
    public static String getLoginPasswordJSON(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while (reader.ready()) {
            line = reader.readLine();
            jb.append(line);
        }
        return jb.toString();
    }

    public static boolean isUserRegistered(LoginJSON loginData) {
        String requestedLogin = loginData.getLogin();
        String requestedPassword = loginData.getPassword();

        String password = UsersDB.getInstance().getUserPassword(requestedLogin);

        if (null == password)
            return false;
        else
            return password.equals(requestedPassword);
    }
}
