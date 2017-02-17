package chat.ui;

import chat.core.Receiver;
import chat.core.ReceiverClient;
import chat.core.Sender;
import chat.core.SendingStatusCallback;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicPanelUI;

public class ChatUI {

    private final static int PORT = 8888;
    private String appName = "Chat";
    ChatUI chatUI;
    private JFrame newFrame = new JFrame(appName);
    private JButton sendMessage;

    private JTextField addressBox;
    private JTextField messageBox;
    private JTextArea chatBox;
    private JTextField usernameChooser;
    private JFrame preFrame;

    private String username;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ChatUI chatUI = new ChatUI();
                chatUI.preDisplay();
                try {
                    Receiver receiver = new Receiver(PORT, 4, new ReceiverClient() {
                        public void processMessage(String senderName, String senderIp, String message) {
                            chatUI.displayMessage(senderName + " (" + senderIp + "): " + message);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayMessage(String s) {
        chatBox.append(s);
    }

    private void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(15);
        JLabel chooseUsernameLabel = new JLabel("Pick a username:");
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(400, 300);
        preFrame.setVisible(true);

    }

    private void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel addressPanel = new JPanel();
        addressPanel.setBackground(Color.BLUE);
        addressPanel.setLayout(new GridBagLayout());
        addressBox = new JTextField(30);

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.BLUE);
        messagePanel.setLayout(new GridBagLayout());
        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        addressPanel.add(addressBox, left);
        addressPanel.add(sendMessage, right);

        messagePanel.add(messageBox, left);

        southPanel.add(BorderLayout.CENTER, addressPanel);
        southPanel.add(BorderLayout.SOUTH, messagePanel);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
    }

    private class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                String address = addressBox.getText();
                String message = messageBox.getText();
                chatBox.append("<" + username + "> to address <" + address + ">:  " + message + "\n");
                Sender sender = new Sender(PORT, 4);
                sender.sendMessage(address, username, message, new SendingStatusCallback() {
                    public void processSendingStatus(boolean sent, String statusMessage) {
                        if (!sent) {
                            chatBox.append("Last message is not send: " + statusMessage);
                        }
                    }
                });

                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    private class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
}