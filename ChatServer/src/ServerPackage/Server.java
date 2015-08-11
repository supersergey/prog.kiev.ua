package ServerPackage;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 11.08.2015.
 */

public class Server extends HttpServlet {
    private final String userdbFile = "c:\\temp\\usersdb.txt";
    private Map<String, String> users = new HashMap<>();

    public Server() throws IOException {

        InputStream stream = new FileInputStream(userdbFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            while (br.ready())
            {
                String[] entry = br.readLine().split("::");
                users.put(entry[0], entry[1]);
            }
            br.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        LoginData loginData = gson.fromJson(request.getReader(), LoginData.class);
        boolean isValidUser = checkUser(loginData);
        if (isValidUser)
            response.sendError(HttpServletResponse.SC_CREATED);
        else
            response.sendError(HttpServletResponse.SC_CONFLICT);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("Hello");
        pw.flush();
    }

    private boolean checkUser(LoginData loginData)
    {
        String login = loginData.getLogin();
        String password = loginData.getPassword();

        if (users.containsKey(login))
            if (users.get(login).equals(password))
                return true;
        return false;
    }
}
