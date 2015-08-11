package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 11.08.2015.
 */
public class PrivateMessagesPane {

    PrivateMessagesPane()
    {
        JEditorPane privateMessages = new JEditorPane();
        privateMessages.setEditable(true);
        privateMessages.setPreferredSize(new Dimension(500, 200));

        JFrame mainFrame = mainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(privateMessages), BorderLayout.PAGE_END);
    }
}
