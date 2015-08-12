package JavaEEChatClient.GUI;

import JavaEEChatClient.NetworkActions.LoginAction;
import JavaEEChatClient.NetworkActions.RegistrationAction;

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

    private JTextField loginField;
    private JPasswordField passwordField;

    private JTextField registrationLoginField;
    private JPasswordField registrationPassword2Field;
    private JPasswordField registrationPasswordField;

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
        loginField = new JTextField(10);
        loginLabel.setLabelFor(loginField);
        loginPanel.add(loginField);

        JLabel passwordLabel = new JLabel(labels[1], JLabel.TRAILING);
        loginPanel.add(passwordLabel);
        passwordField = new JPasswordField(10);
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
        registrationLoginField = new JTextField(10);
        loginLabel.setLabelFor(registrationLoginField);
        loginPanel.add(registrationLoginField );

        JLabel passwordLabel = new JLabel(labels[1], JLabel.TRAILING);
        loginPanel.add(passwordLabel);
        registrationPasswordField = new JPasswordField(10);
        loginLabel.setLabelFor(passwordLabel);
        loginPanel.add(registrationPasswordField);

        JLabel password2Label = new JLabel(labels[2], JLabel.TRAILING);
        loginPanel.add(password2Label);
         registrationPassword2Field = new JPasswordField(10);
        loginLabel.setLabelFor(password2Label);
        loginPanel.add(registrationPassword2Field);

        JButton registerButton =new JButton("Register");
        loginPanel.add(registerButton);
        registerButton.addActionListener(new RegistrationAction());

        JButton cancelButton = new JButton("Cancel");
        loginPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });


//Lay out the panel.
        SpringUtilities.makeCompactGrid(loginPanel,
                numPairs + 1, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        return loginPanel;
    }

    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getRegistrationLoginField() {
        return registrationLoginField;
    }

    public JPasswordField getRegistrationPassword2Field() {
        return registrationPassword2Field;
    }

    public JPasswordField getRegistrationPasswordField() {
        return registrationPasswordField;
    }
}
