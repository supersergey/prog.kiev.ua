package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 10.08.2015.
 */
public class MainGUI {

    private static MainGUI ourInstance = new MainGUI();
    private ChatRoomsPane chatRoomsPane;
    private UsersOnlinePane usersOnlinePane;
    private PrivateMessagesPane privateMessagesPane;
    private AllMessagesPane allMessagesPane;
    private JFrame mainFrame;

    public static MainGUI getInstance() {
        return ourInstance;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void createGUI() {

        mainFrame = new JFrame("JavaEE chat");

         // главное окно
        mainFrame.setMinimumSize(new Dimension(300, 200));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatRoomsPane = new JavaEEChatClient.GUI.ChatRoomsPane();
        usersOnlinePane = new JavaEEChatClient.GUI.UsersOnlinePane();
        privateMessagesPane = new JavaEEChatClient.GUI.PrivateMessagesPane();
        allMessagesPane = new JavaEEChatClient.GUI.AllMessagesPane();

        // new LoginPane();
        mainFrame.pack();

        // mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    private MainGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

}
