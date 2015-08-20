package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.GetMessagesAction;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;

/**
 * Created by user on 11.08.2015.
 */
public class AllMessagesPane {
    private JEditorPane allMessagesPane;
    AllMessagesPane() {
            allMessagesPane = new JEditorPane();
            allMessagesPane.setContentType("text/html;Content-Type=UTF-8");
            allMessagesPane.setEditable(false);
            HTMLEditorKit kit = new HTMLEditorKit();
            allMessagesPane.setEditorKit(kit);
            StyleSheet styleSheet = kit.getStyleSheet();
            styleSheet.addRule("body {font-family:Arial; }");
            DefaultCaret caret = (DefaultCaret) allMessagesPane.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            JFrame mainFrame = MainGUI.getInstance().getMainFrame();
            mainFrame.add(new JScrollPane(allMessagesPane), BorderLayout.CENTER);
            Runnable getMessages = new GetMessagesAction();
        }
    public JEditorPane getAllMessagesPane() {
        return allMessagesPane;
    }
}



