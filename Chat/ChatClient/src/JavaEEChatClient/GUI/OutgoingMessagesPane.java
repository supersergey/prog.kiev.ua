package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.ChangeChatRoomAction;
import JavaEEChatClient.NetworkActions.PostMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by user on 11.08.2015.
 */
public class OutgoingMessagesPane extends KeyAdapter {

    private JTextArea privateMessages;
    private int offset = 0;

    OutgoingMessagesPane() {
        privateMessages = new JTextArea(5, 20);
        privateMessages.setEditable(true);
        privateMessages.setPreferredSize(new Dimension(20, 1400));
        privateMessages.addKeyListener(this);

        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(privateMessages), BorderLayout.PAGE_END);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int length = privateMessages.getText().length();
            try {
                String message = privateMessages.getText(offset, length - offset);
                String[] commands = message.split(" ", 2);
                if (commands[0].equals("/room"))
                    ChangeChatRoomAction.doChangeGet(commands[1]);
                else
                    PostMessage.doPost(message);
            } catch (BadLocationException ignored) {
            }
            offset = length + 1;
        }
    }
}
