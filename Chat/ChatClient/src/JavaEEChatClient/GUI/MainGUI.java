package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.ExitAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 10.08.2015.
 */
public class MainGUI {

    private static MainGUI ourInstance = new MainGUI();
    private ChatRoomsPane chatRoomsPane;
    private UsersOnlinePane usersOnlinePane;
    private OutgoingMessagesPane outgoingMessagesPane;
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
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatRoomsPane = new JavaEEChatClient.GUI.ChatRoomsPane();
        usersOnlinePane = new JavaEEChatClient.GUI.UsersOnlinePane();
        outgoingMessagesPane = new OutgoingMessagesPane();
        allMessagesPane = new JavaEEChatClient.GUI.AllMessagesPane();

        // new LoginPane();
        mainFrame.pack();

        // mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                            ExitAction.doExitGet();
                                        }
                                    }
        );
    }

    private MainGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    public ChatRoomsPane getChatRoomsPane() {
        return chatRoomsPane;
    }

    public UsersOnlinePane getUsersOnlinePane() {
        return usersOnlinePane;
    }

    public OutgoingMessagesPane getOutgoingMessagesPane() {
        return outgoingMessagesPane;
    }

    public AllMessagesPane getAllMessagesPane() {
        return allMessagesPane;
    }
}
