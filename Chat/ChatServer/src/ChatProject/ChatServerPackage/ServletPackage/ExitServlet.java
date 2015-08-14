package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergey on 12.08.2015.
 */
public class ExitServlet extends HttpServlet {

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String room = request.getParameter("room");
        ChatRoom chatRoom = ChatServer.getInstance().getChatRoom(room);
        User user = chatRoom.getMember(name);
        if (null!=chatRoom && null!=user)
        {
            chatRoom.getMembers().remove(name);
            ChatServer.getInstance().getUsers().remove(name);
            chatRoom.addMessage(new ChatMessage(new User("Server", chatRoom), "User " + name + " has left."));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);

    }
}
