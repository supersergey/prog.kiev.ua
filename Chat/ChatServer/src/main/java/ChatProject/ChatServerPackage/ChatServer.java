package ChatProject.ChatServerPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12.08.2015.
 */
public class ChatServer {
    private Map<String, ChatRoom> chatRooms = new HashMap<>();
    private static ChatServer ourInstance = new ChatServer();

    public static ChatServer getInstance() {
        return ourInstance;
    }

    private ChatServer() {
        ChatRoom defaultChatRoom = new ChatRoom("default");
        User userServer = new User("Server", defaultChatRoom, false);
        defaultChatRoom.addMember(userServer);
        ChatMessage welcomeMessage = new ChatMessage(userServer, "Chat server engine has started successfully. Welcome to Java Chat");
        defaultChatRoom.addMessage(welcomeMessage);
        chatRooms.put(defaultChatRoom.getName(), defaultChatRoom);
    }

    public ChatRoom getChatRoom(String chatRoomName) {
        return chatRooms.get(chatRoomName);
    }

    public Map<String, ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public ChatRoom findChatRoomByUser(String username) {
        ChatRoom result = null;
        for (Map.Entry<String, ChatRoom> cr : chatRooms.entrySet())
            if (cr.getValue().contrainsMember(username)) {
                result = cr.getValue();
                break;
            }
        return result;
    }

    public ChatRoom getDefaultChatRoom() {
        return chatRooms.get("default");
    }

    public Map<String, User> getAllUsers() {
        Map<String, User> allUsers = new HashMap<>();
        for (Map.Entry<String, ChatRoom> cr : chatRooms.entrySet())
            allUsers.putAll(cr.getValue().getOnlineMembers());
        return allUsers;
    }

    public void addNewRoom(ChatRoom newRoom) {
        chatRooms.put(newRoom.getName(), newRoom);
    }

}
