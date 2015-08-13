package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 11.08.2015.
 */
public class ChatRoomsPane {
    ChatRoomsPane()
    {
        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(createChatRoomsPane(), BorderLayout.LINE_START);
    }

    private Component createChatRoomsPane()
    {
        DefaultListModel<String> chatRoomsListModel = new DefaultListModel<>();
        chatRoomsListModel.addElement("JavaEEChatClient.ChatClient room #1");
        chatRoomsListModel.addElement("JavaEEChatClient.ChatClient room #2");
        chatRoomsListModel.addElement("JavaEEChatClient.ChatClient room #3");
        JList<String> chatRoomsList = new JList<>(chatRoomsListModel);
        return new JScrollPane(chatRoomsList);
    }

}
