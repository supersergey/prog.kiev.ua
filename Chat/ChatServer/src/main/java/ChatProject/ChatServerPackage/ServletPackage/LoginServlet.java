package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.User;
import JSON.LoginJSON;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import JSON.MessageJSON.*;


/**
 * Created by user on 11.08.2015.
 */

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginPasswordJSON = CommonOperations.getLoginPasswordJSON(request);
        Gson gson = new Gson();
        LoginJSON loginData = gson.fromJson(loginPasswordJSON, LoginJSON.class);

        boolean isValidUser = CommonOperations.isUserRegistered(loginData);
        if (isValidUser)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            ChatRoom defaultRoom = ChatServer.getInstance().getDefaultChatRoom();
            if (null!=defaultRoom)
            {
                ChatMessage welcomeMessage = new ChatMessage(defaultRoom.getMember("Server"), "User " + loginData.getLogin() + " has joined " + defaultRoom.getName() + " chatroom.");
                defaultRoom.addMessage(welcomeMessage);
                User newUser = new User(loginData.getLogin(), defaultRoom);
                defaultRoom.addMember(newUser);
            }
        }
        else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
