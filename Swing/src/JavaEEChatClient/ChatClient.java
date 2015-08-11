package JavaEEChatClient;

import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.GUI.mainGUI;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 10.08.2015.
 */
public class ChatClient {
    private List<ChatRoom> chatRoomList;
    private List<User> onlineUsers;
    private mainGUI chatGUI;
    private LoginGUI loginGUI;

    public static void main(String[] args)
    {
        ChatClient thisChat = new ChatClient();
    }

    public ChatClient() {
        loginGUI = LoginGUI.getInstance();
        //chatGUI = mainGUI.getInstance();
        try
        {
            ChatConnection http = new ChatConnection("localhost:8080");
        }
        catch (IOException e)
        {
            System.out.println("Could not establish connection");
            e.printStackTrace();
        }

    }
}
