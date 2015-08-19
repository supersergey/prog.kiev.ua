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
 * Created by user on 19.08.2015.
 */
public class ChangeRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("name");
        String newChatRoomName = req.getParameter("room");

        User requestedUser = ChatServer.getInstance().getAllUsers().get(username);
        if (null == requestedUser)
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            ChatRoom newChatRoom = ChatServer.getInstance().getChatRoom(newChatRoomName);
            if (null == newChatRoom) {
                newChatRoom = new ChatRoom(newChatRoomName);
            }
            ChatRoom oldChatRoom = requestedUser.getCurrentRoom();
            oldChatRoom.removeMember(requestedUser.getName());
            newChatRoom.addMember(requestedUser);
            newChatRoom.addMessage(new ChatMessage(new User("Server", newChatRoom), "Welcome to the chat room " + newChatRoomName + "!"));
            ChatServer.getInstance().addNewRoom(newChatRoom);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}