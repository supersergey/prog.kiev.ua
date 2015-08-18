package ChatProject.ChatServerPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12.08.2015.
 */
public class ChatServer {
    private Map<String, ChatRoom> chatRooms = new HashMap<>();
    private static ChatServer ourInstance = new ChatServer();
    //private Map<String, User> users = new HashMap<>();

    public static ChatServer getInstance() {
        return ourInstance;
    }

    private ChatServer() {
        chatRooms = new HashMap<>();
        ChatRoom defaultChatRoom = new ChatRoom("default");
        chatRooms.put("default", defaultChatRoom);
        // users.put("Server", new User("Server", defaultChatRoom));
    }

    public ChatRoom getChatRoom(String name)
    {
        return chatRooms.get(name);
    }

    /*public User getUser(String name)
    {
        return users.get(name);
    }

    public Map<String, User> getUsers() {
        return users;
    }*/

    public Map<String, ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
