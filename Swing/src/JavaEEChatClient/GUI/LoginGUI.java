package JavaEEChatClient.GUI;

import javax.swing.*;

/**
 * Created by user on 11.08.2015.
 */
public class LoginGUI {
    private static LoginGUI ourInstance = new LoginGUI();
    private LoginPane loginPane;
    private JFrame mainFrame;

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public static LoginGUI getInstance() {
        return ourInstance;
    }

    private LoginGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    private void createGUI()
    {
        mainFrame = new JFrame("Java EE chat login");
        loginPane = new LoginPane();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }


}
