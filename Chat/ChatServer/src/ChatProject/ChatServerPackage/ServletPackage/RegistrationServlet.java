package ChatProject.ChatServerPackage.ServletPackage;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 12.08.2015.
 */
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginPasswordJSON = CommonOperations.getLoginPasswordJSON(request);
        Gson gson = new Gson();
        LoginData loginData = gson.fromJson(loginPasswordJSON, LoginData.class);

        boolean isUserRegistered = UsersDB.getInstance().getUsers().containsKey(loginData.getLogin());
        if (!isUserRegistered)
        {
            UsersDB.getInstance().addNewUser(loginData.getLogin(), loginData.getPassword());
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
