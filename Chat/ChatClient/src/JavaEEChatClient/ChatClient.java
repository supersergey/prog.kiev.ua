package JavaEEChatClient;

import JavaEEChatClient.GUI.LoginGUI;
import JavaEEChatClient.GUI.MainGUI;

import java.util.List;

/**
 * Created by user on 10.08.2015.
 */
public class ChatClient {
    private static MainGUI mainGUI;
    private static LoginGUI loginGUI;

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

}
