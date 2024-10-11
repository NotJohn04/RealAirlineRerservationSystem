package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class AdminLogin {

    JFrame f = new JFrame();
    JLabel AdminLoginTitle = new JLabel("Admin Login");
    JLabel UserName = new JLabel("Username");
    JLabel Password = new JLabel("Password");

    JTextField InputUserName = new JTextField();
    JPasswordField InputPassword = new JPasswordField(); // Use JPasswordField for password input
    JButton Login = new JButton("Login");
    JButton Back = new JButton("Back");

    AdminLogin() {

        // Set the background color
        f.getContentPane().setBackground(new Color(0xF8BBD0)); // Pink background color
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around elements
        gbc.anchor = GridBagConstraints.CENTER; // Center everything

        // Title settings
        AdminLoginTitle.setFont(new Font("Arial", Font.BOLD, 30));
        AdminLoginTitle.setForeground(new Color(0xe91e63)); // Dark pink color for title
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        f.add(AdminLoginTitle, gbc);

        // Username label and input
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        UserName.setFont(new Font("Arial", Font.PLAIN, 18));
        UserName.setForeground(new Color(0xe91e63)); // Dark pink
        f.add(UserName, gbc);

        gbc.gridx = 1;
        InputUserName.setPreferredSize(new Dimension(200, 30));
        f.add(InputUserName, gbc);

        // Password label and input
        gbc.gridy = 2;
        gbc.gridx = 0;
        Password.setFont(new Font("Arial", Font.PLAIN, 18));
        Password.setForeground(new Color(0xe91e63)); // Dark pink
        f.add(Password, gbc);

        gbc.gridx = 1;
        InputPassword.setPreferredSize(new Dimension(200, 30));
        f.add(InputPassword, gbc);

        // Login and Back buttons
        gbc.gridy = 3;
        gbc.gridx = 0;
        Login.setFont(new Font("Arial", Font.PLAIN, 16));
        Login.setBackground(new Color(0xe91e63)); // Dark pink background
        Login.setForeground(Color.WHITE); // White text
        Login.setOpaque(true);
        Login.setBorderPainted(false);
        Login.setPreferredSize(new Dimension(100, 30));
        f.add(Login, gbc);

        gbc.gridx = 1;
        Back.setFont(new Font("Arial", Font.PLAIN, 16));
        Back.setBackground(new Color(0xe91e63)); // Dark pink background
        Back.setForeground(Color.WHITE); // White text
        Back.setOpaque(true);
        Back.setBorderPainted(false);
        Back.setPreferredSize(new Dimension(100, 30));
        f.add(Back, gbc);

        // Frame settings
        f.setTitle("Admin Login");
        f.setSize(500, 400);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        final int[] count = {0};

        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                f.dispose();
            }
        });

        Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = InputUserName.getText();
                String password = new String(InputPassword.getPassword());

                if (FileUtil.checkLocked("AdminLogin.txt", username)) {
                    JOptionPane.showMessageDialog(null, "User is locked, please try again later.");
                    f.dispose();
                    return;
                }

                if (!FileUtil.checkUser("AdminLogin.txt", username)) {
                    JOptionPane.showMessageDialog(null, "User does not exist");
                }

                if (FileUtil.validateLogin("AdminLogin.txt", username, password)) {
                    JOptionPane.showMessageDialog(null, "Admin Login Successful");
                    new AdminScreen();
                    f.dispose();
                } else {
                    count[0]++;
                    JOptionPane.showMessageDialog(null, "Invalid password");

                    if (count[0] == 3) {
                        FileUtil.lockUser("AdminLogin.txt", username);
                        JOptionPane.showMessageDialog(null, "Too many attempts. Please try again later.");
                        f.dispose();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
