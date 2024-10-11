package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerLogin {

    JFrame f = new JFrame();
    JLabel MainTitle = new JLabel("Customer Login");
    JLabel UserName = new JLabel("Username");
    JLabel Password = new JLabel("Password");

    JTextField InputUserName = new JTextField(15);
    JPasswordField InputPassword = new JPasswordField(15); // Use JPasswordField for password input
    JButton Login = new JButton("Login");
    JButton Register = new JButton("Register");
    JButton back = new JButton("Back");

    CustomerLogin() {
        // Set the pink background color for the frame
        f.getContentPane().setBackground(new Color(0xF8BBD0));
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around the components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title setup
        MainTitle.setFont(new Font("Arial", Font.BOLD, 24));
        MainTitle.setForeground(new Color(0xe91e63));  // Pink color for title text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span title across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        f.add(MainTitle, gbc);

        // Username Label
        UserName.setFont(new Font("Arial", Font.PLAIN, 16));
        UserName.setForeground(new Color(0xe91e63));  // Pink color for labels
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;  // Reset grid width for input fields
        gbc.anchor = GridBagConstraints.EAST;
        f.add(UserName, gbc);

        // Username Input
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        f.add(InputUserName, gbc);

        // Password Label
        Password.setFont(new Font("Arial", Font.PLAIN, 16));
        Password.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        f.add(Password, gbc);

        // Password Input
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        f.add(InputPassword, gbc);

        // Login Button
        Login.setBackground(new Color(0xe91e63));
        Login.setForeground(Color.WHITE);
        Login.setOpaque(true);
        Login.setBorderPainted(false);
        Login.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 10);
        f.add(Login, gbc);

        // Register Button
        Register.setBackground(new Color(0xe91e63));
        Register.setForeground(Color.WHITE);
        Register.setOpaque(true);
        Register.setBorderPainted(false);
        Register.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 0, 0);
        f.add(Register, gbc);

        // Back Button
        back.setBackground(new Color(0xe91e63));
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        back.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);  // Add space between buttons
        f.add(back, gbc);

        // Frame setup
        f.setTitle("Customer Login Screen");
        f.setSize(500, 300);
        f.setLocationRelativeTo(null);  // Center the frame on the screen
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        final int[] count = {0};

        // Add action listeners to buttons
        Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = InputUserName.getText();
                String password = new String(InputPassword.getPassword());

                if (FileUtil.checkLocked("CustomerLogin.txt", username)) {
                    JOptionPane.showMessageDialog(null, "User is locked, please contact the admin to unlock your account.");
                    f.dispose();
                    return;
                }

                if (!FileUtil.checkUser("CustomerLogin.txt", username)) {
                    JOptionPane.showMessageDialog(null, "User does not exist, please register");
                    new RegisterScreen();
                    f.dispose();
                    return;
                }

                if (FileUtil.validateLogin("CustomerLogin.txt", username, password)) {
                    JOptionPane.showMessageDialog(null, "Customer Login Successful");
                    new CustomerScreen(username);
                    f.dispose();
                    return;
                } else {
                    count[0]++;
                    JOptionPane.showMessageDialog(null, "Invalid password");

                    if (count[0] == 3) {
                        FileUtil.lockUser("CustomerLogin.txt", username);
                        JOptionPane.showMessageDialog(null, "Too many attempts. Please contact the admin to unlock your account.");
                        f.dispose();
                    }
                }
            }
        });

        Register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterScreen();
                f.dispose();
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                f.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new CustomerLogin();
    }
}
