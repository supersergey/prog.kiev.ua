package JavaEEChatClient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by user on 11.08.2015.
 */
public class LoginPane {
    private JFrame mainFrame;

    String login;
    String password;

    LoginPane() {//Create and populate the panel.
        mainFrame = LoginGUI.getInstance().getMainFrame();

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add(createLoginTab(), "Login");
        jTabbedPane.add(createRegistrationTab(), "Register");
        mainFrame.add(jTabbedPane);
    }

    private Component createLoginTab()
    {
        JPanel loginPanel = new JPanel(new SpringLayout());
        final String[] labels = {"Login: ", "Password: "};
        final int numPairs = labels.length;

        JLabel loginLabel = new JLabel(labels[0], JLabel.TRAILING);
        loginPanel.add(loginLabel);
        final JTextField loginField = new JTextField(10);
        loginLabel.setLabelFor(loginField);
        loginPanel.add(loginField);

        JLabel passwordLabel = new JLabel(labels[1], JLabel.TRAILING);
        loginPanel.add(passwordLabel);
        final JPasswordField passwordField = new JPasswordField(10);
        loginLabel.setLabelFor(passwordLabel);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new LoginAction(loginField.getText(), new String(passwordField.getPassword())));
        loginPanel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        loginPanel.add(cancelButton);

//Lay out the panel.
        SpringUtilities.makeCompactGrid(loginPanel,
                numPairs + 1, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad




        return loginPanel;
    }

    private Component createRegistrationTab()
    {
        JPanel loginPanel = new JPanel(new SpringLayout());
        final String[] labels = {"Login: ", "Password: ", "Repeat password: "};
        final int numPairs = labels.length;

        JLabel loginLabel = new JLabel(labels[0], JLabel.TRAILING);
        loginPanel.add(loginLabel);
        JTextField textField = new JTextField(10);
        loginLabel.setLabelFor(textField);
        loginPanel.add(textField);

        JLabel passwordLabel = new JLabel(labels[1], JLabel.TRAILING);
        loginPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(10);
        loginLabel.setLabelFor(passwordLabel);
        loginPanel.add(passwordField);

        JLabel password2Label = new JLabel(labels[2], JLabel.TRAILING);
        loginPanel.add(password2Label);
        JPasswordField password2Field = new JPasswordField(10);
        loginLabel.setLabelFor(password2Label);
        loginPanel.add(password2Field);

        loginPanel.add(new JButton("Register"));
        loginPanel.add(new JButton("Cancel"));


//Lay out the panel.
        SpringUtilities.makeCompactGrid(loginPanel,
                numPairs + 1, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        return loginPanel;
    }



}
