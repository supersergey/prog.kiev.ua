package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;
import JSON.MessageJSON;
import JSON.MessagesJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 12.08.2015.
 */

public class GetMessagesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String user = request.getParameter("user");
        String room = request.getParameter("room");
        boolean isValidRequest = true;
        ChatRoom chatRoom = ChatServer.getInstance().getChatRoom(room);
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
            MessagesJSON messagesJSON = new MessagesJSON();
            for (ChatMessage m : chatRoom.getMessageList())
            {
                MessageJSON messageJSON = new MessageJSON();
                messageJSON.setName(m.getFrom().getName());
                messageJSON.setBody(m.getBody());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
                messageJSON.setDate(sdf.format(m.getTimestamp()));
                messagesJSON.getMessages().add(messageJSON);
            }
            String JSONresponse = "";
            JSONresponse = reply.toJson(messagesJSON, MessagesJSON.class);
            response.getWriter().print(JSONresponse);
        }
    }
}
