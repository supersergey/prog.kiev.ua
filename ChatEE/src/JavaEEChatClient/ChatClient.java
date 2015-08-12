package JavaEEChatClient;

import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.GUI.MainGUI;

import java.util.List;

/**
 * Created by user on 10.08.2015.
 */
public class ChatClient {
    private static List<ChatRoom> chatRoomList;
    private static List<User> onlineUsers;
    private static MainGUI mainGUI;
    private static LoginGUI loginGUI;
    private static User currentUser;

    public static void main(String[] args)
    {
        runLoginGUI();
    }

    public static void runLoginGUI()
    {
        loginGUI = LoginGUI.getInstance();
    }

    public static void runMainGUI()
    {
        mainGUI = MainGUI.getInstance();
    }

    public static void setCurrentUser(User user)
    {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
