package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;
import JSON.UserJSON;
import JSON.UsersJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 13.08.2015.
 */
public class GetOnlineUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersJSON usersJSON = new UsersJSON();
        for (ChatRoom room : ChatServer.getInstance().getChatRooms().values())
        {
            for (User user : room.getOnlineMembers().values())
            {
                UserJSON userJSON = new UserJSON();
                userJSON.setName(user.getName());
                usersJSON.getUsers().add(userJSON);
            }
        }
        Gson gson = new GsonBuilder().create();
        String responseJSON = gson.toJson(usersJSON, UsersJSON.class);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(responseJSON);
    }
}
