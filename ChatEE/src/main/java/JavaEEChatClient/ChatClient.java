package JavaEEChatClient;

import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.GUI.MainGUI;

import java.util.List;

/**
 * Created by user on 10.08.2015.
 */
public class ChatClient {
    private List<ChatRoom> chatRoomList;
    private List<User> onlineUsers;
    private MainGUI chatGUI;
    private LoginGUI loginGUI;

    public static void main(String[] args)
    {
        ChatClient thisChat = new ChatClient();
    }

    public ChatClient() {
        loginGUI = LoginGUI.getInstance();
    }
}
