package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by user on 11.08.2015.
 */
public class AllMessagesPane {
    AllMessagesPane()
    {
        JEditorPane allMessagesPane = new JEditorPane();
        allMessagesPane.setEditable(false);

        try
        {
            URL url = new URL("https://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html");
            allMessagesPane.setPage(url);
        }
        catch (IOException e) {e.printStackTrace();}
        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(new JScrollPane(allMessagesPane), BorderLayout.CENTER);
    }
}
