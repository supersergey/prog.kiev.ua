package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.GetOnlineUsersAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by user on 11.08.2015.
 */
public class UsersOnlinePane {

    private DefaultListModel<String> usersOnlineModel = new DefaultListModel<>();

    UsersOnlinePane()
    {
        JFrame mainFrame = MainGUI.getInstance().getMainFrame();
        mainFrame.add(createUsersOnlinePanel(), BorderLayout.LINE_END);
        new GetOnlineUsersAction();
    }

    private Component createUsersOnlinePanel()
    {
        /*usersOnlineModel.addElement("user #1");
        usersOnlineModel.addElement("user #2");
        usersOnlineModel.addElement("user #3");*/
        JList<String> usersOnlineList = new JList<>(usersOnlineModel);

        /*MouseListener mouseListener = new MouseAdapter() {
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
        usersOnlineList.addMouseListener(mouseListener);*/
        return new JScrollPane(usersOnlineList);
    }

    public void addUser(String name)
    {
        usersOnlineModel.addElement(name);
    }

    public void clearUsers()
    {
        usersOnlineModel.clear();
    }
}
