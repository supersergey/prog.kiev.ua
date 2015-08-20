package ChatProject.ChatServerPackage.ServletPackage;

import ChatProject.ChatServerPackage.ChatRoom;
import ChatProject.ChatServerPackage.ChatServer;
import ChatProject.ChatServerPackage.User;
import JSON.RoomJSON;
import JSON.RoomsJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sergey on 19.08.2015.
 */
public class GetRoomsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUserName = req.getParameter("user");
        String requestRoomName = req.getParameter("room");
        ChatRoom room = ChatServer.getInstance().getChatRoom(requestRoomName);
        if (null==room)
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        else
        {
            User user = room.getMember(requestUserName);
            if (null==user)
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            else {
                Gson gson = new GsonBuilder().create();
                RoomsJSON roomsJSON = new RoomsJSON();
                for (Map.Entry<String, ChatRoom> cr : ChatServer.getInstance().getChatRooms().entrySet())
                {
                    RoomJSON roomJSON = new RoomJSON();
                    roomJSON.setRoomName(cr.getValue().getName());
                    roomsJSON.getRooms().add(roomJSON);
                }
                String JSONresponse = gson.toJson(roomsJSON, RoomsJSON.class);
                byte[] buf = JSONresponse.getBytes("UTF-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getOutputStream().write(buf);
                resp.getOutputStream().close();
            }
        }
    }
}
