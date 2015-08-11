package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by user on 11.08.2015.
 */
public class UsersOnlinePane {

    UsersOnlinePane()
    {
        JFrame mainFrame = mainGUI.getInstance().getMainFrame();
        mainFrame.add(createUsersOnlinePanel(), BorderLayout.LINE_END);
    }

    private Component createUsersOnlinePanel()
    {
        DefaultListModel<String> usersOnlineModel = new DefaultListModel<>();
        usersOnlineModel.addElement("user #1");
        usersOnlineModel.addElement("user #2");
        usersOnlineModel.addElement("user #3");
        JList<String> usersOnlineList = new JList<>(usersOnlineModel);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 1) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());
                    }
                }
            }
        };
        usersOnlineList.addMouseListener(mouseListener);


        return new JScrollPane(usersOnlineList);
    }

}
