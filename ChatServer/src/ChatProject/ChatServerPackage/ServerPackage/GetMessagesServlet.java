package ChatProject.ChatServerPackage.ServerPackage;

import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 12.08.2015.
 */
@WebServlet(name = "GetMessagesServlet")
public class GetMessagesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String user = request.getParameter("user");
        String room = request.getParameter("room");
        boolean isValidRequest = true;
        ChatRoom chatRoom = ChatServer.getInstance().getCharRoom(room);
        User chatMember;
        if (null==chatRoom)
            isValidRequest = false;
        else
        {
            chatMember = chatRoom.getMember(user);
            if (null==chatMember)
                isValidRequest = false;
        }
        if (!isValidRequest)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson reply = gsonBuilder.create();
            StringBuilder sb = new StringBuilder();
            reply.toJson(chatRoom.getMessageList(), )
            ChatMessage[] chatMessages = new ChatMessage[]
            for (ChatMessage m : )
                sb.append(reply.toJson(m));
            response.setStatus(HttpServletResponse.SC_OK);
            response.
        }
    }
}
