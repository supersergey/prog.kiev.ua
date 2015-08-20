package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.GetRoomsListAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 11.08.2015.
 */
public class ChatRoomsPane {

    private JFrame mainFrame;
    private JTextArea jTextArea;

    ChatRoomsPane()
    {
        mainFrame = MainGUI.getInstance().getMainFrame();
        jTextArea = new JTextArea(2, 20);
        mainFrame.add(jTextArea, BorderLayout.LINE_START);
        new GetRoomsListAction();
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }
}
