package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatMessage;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;
import JSON.MessageJSON;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 13.08.2015.
 */
public class PostMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(request.getReader());
        StringBuilder sb = new StringBuilder();
        while (br.ready())
        {
            sb.append(br.readLine());
        }

        Gson json = new Gson();
        MessageJSON messageJSON = json.fromJson(sb.toString(), MessageJSON.class);
        User user = ChatServer.getInstance().getUser(messageJSON.getName());
        if (null!=user)
        {
            ChatMessage chatMessage = new ChatMessage(user, messageJSON.getBody());
            ChatServer.getInstance().getChatRoom("default").addMessage(chatMessage);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

}
