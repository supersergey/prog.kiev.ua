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
        String requestedChatRoom = request.getParameter("chatroom");
        String requestedUser = request.getParameter("username");

        ChatRoom chatRoom = ChatServer.getInstance().getChatRoom(requestedChatRoom);
        if (null==chatRoom)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else
        if (null==chatRoom.getMember(requestedUser))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            UsersJSON usersJSON = new UsersJSON();
            for (User user : chatRoom.getOnlineMembers().values())
                {
                    UserJSON userJSON = new UserJSON();
                    userJSON.setName(user.getName());
                    usersJSON.getUsers().add(userJSON);
                }
            Gson gson = new GsonBuilder().create();
            String responseJSON = gson.toJson(usersJSON, UsersJSON.class);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(responseJSON);
        }

    }
}
