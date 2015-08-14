package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.PostMessage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by user on 11.08.2015.
 */
public class PrivateMessagesPane extends KeyAdapter {

    private JTextArea privateMessages;
    private int offset = 0;

    PrivateMessagesPane()
    {
        privateMessages = new JTextArea(5, 100);
        privateMessages.setEditable(true);
        privateMessages.setPreferredSize(new Dimension(500, 1400));
        privateMessages.addKeyListener(this);

        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(privateMessages), BorderLayout.PAGE_END);
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            int length = privateMessages.getText().length();
            try
            {
                String message = privateMessages.getText(offset, length-offset);
                // message = message.substring(0, message.length()-2); // remove trailing \r\n
                PostMessage.doPost(message);
            }
            catch (BadLocationException ignored) {}
            offset = length + 1;
        }
    }
}
