package ChatProject.ChatServerPackage.ServerPackage;

import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created by user on 11.08.2015.
 */

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginPasswordJSON = CommonOperations.getLoginPasswordJSON(request);
        Gson gson = new Gson();
        LoginData loginData = gson.fromJson(loginPasswordJSON, LoginData.class);

        boolean isValidUser = CommonOperations.isUserRegistered(loginData);
        if (isValidUser)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            ChatRoom defaultRoom = ChatServer.getInstance().getCharRoom("default");
            if (null!=defaultRoom)
            {
                ChatMessage welcomeMessage = new ChatMessage(ChatServer.getInstance().getUser("Server"), "User " + loginData.getLogin() + " has joined this chatroom.");
                defaultRoom.addMessage(welcomeMessage);
                defaultRoom.addMember(new User(loginData.getLogin()));
            }
        }
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

/*    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("Hello");
        pw.flush();
    }*/


}
