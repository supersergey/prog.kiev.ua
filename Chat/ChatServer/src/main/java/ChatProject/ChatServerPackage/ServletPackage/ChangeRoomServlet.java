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
            ChatRoom oldChatRoom = requestedUser.getCurrentRoom();
            ChatRoom newChatRoom = ChatServer.getInstance().getChatRoom(newChatRoomName);
            if (null == newChatRoom) {
                newChatRoom = new ChatRoom(newChatRoomName);
            }
            requestedUser.setCurrentRoom(newChatRoom);
            oldChatRoom.removeMember(requestedUser.getName());
            newChatRoom.addMember(requestedUser);
            newChatRoom.addMessage(new ChatMessage(new User("Server", newChatRoom), requestedUser.getName() + " joined the chat room " + newChatRoomName + ". Welcome!"));
            ChatServer.getInstance().addNewRoom(newChatRoom);
            if (oldChatRoom.getOnlineMembers().isEmpty() && !oldChatRoom.equals(ChatServer.getInstance().getDefaultChatRoom()))
                ChatServer.getInstance().removeChatRoom(oldChatRoom);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}