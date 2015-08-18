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

    public static boolean isUserRegistered(LoginJSON loginData)
    {
        String login = loginData.getLogin();
        String password = loginData.getPassword();

        if (UsersDB.getInstance().getUsers().containsKey(login))
            if (UsersDB.getInstance().getUsers().get(login).equals(password))
                return true;
        return false;
    }
}
