package ChatProject.ChatServerPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12.08.2015.
 */
public class ChatServer {
    private Map<String, ChatRoom> chatRooms = new HashMap<>();
    private static ChatServer ourInstance = new ChatServer();
    private Map<String, User> users = new HashMap<>();

    public static ChatServer getInstance() {
        return ourInstance;
    }

    private ChatServer() {
        chatRooms = new HashMap<>();
        chatRooms.put("default", new ChatRoom("default"));
        users.put("Server", new User("Server"));
    }

    public ChatRoom getCharRoom(String name)
    {
        return chatRooms.get(name);
    }

    public User getUser(String name)
    {
        return users.get(name);
    }
}
