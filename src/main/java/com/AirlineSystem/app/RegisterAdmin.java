package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterAdmin {

    JFrame frame = new JFrame();
    JLabel title = new JLabel("Register Admin");
    JLabel username = new JLabel("Username");
    JLabel password = new JLabel("Password");
    JTextField usernameInput = new JTextField(15);  // Set preferred size for input fields
    JPasswordField passwordInput = new JPasswordField(15);  // Use JPasswordField for password
    JButton registerButton = new JButton("Register Admin");
    JButton backButton = new JButton("Back");

    RegisterAdmin() {
        // Set the pink background color for the frame
        frame.getContentPane().setBackground(new Color(0xF8BBD0));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span title across two columns
        gbc.insets = new Insets(10, 0, 20, 0);  // Add padding
        gbc.anchor = GridBagConstraints.CENTER;  // Center the title
        frame.add(title, gbc);

        // Username Label
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;  // Reset grid width for next components
        gbc.anchor = GridBagConstraints.EAST;  // Align to the right of the middle
        gbc.insets = new Insets(0, 10, 10, 10);  // Adjust spacing
        frame.add(username, gbc);

        // Username Input
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;  // Align to the left of the middle
        gbc.insets = new Insets(0, 0, 10, 10);
        frame.add(usernameInput, gbc);

        // Password Label
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 10, 10, 10);  // Adjust spacing
        frame.add(password, gbc);

        // Password Input
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 10);
        frame.add(passwordInput, gbc);

        // Register Button
        registerButton.setBackground(new Color(0xe91e63));
        registerButton.setForeground(Color.WHITE);
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 10);
        frame.add(registerButton, gbc);

        // Back Button
        backButton.setBackground(new Color(0xe91e63));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 10, 0, 0);
        frame.add(backButton, gbc);

        // Frame setup
        frame.setTitle("Admin Register Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);

        // Register button functionality
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = new String(passwordInput.getPassword());

                if (!username.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Username must contain both letters and numbers, and only letters and numbers.");
                } else if (password.length() < 8 || password.length() > 10 || username.length() < 8 || username.length() > 10) {
                    JOptionPane.showMessageDialog(null, "Password and Username must be between 8 and 10 characters long.");
                } 
                else if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])[a-zA-Z\\d@#$%^&+=]{8,10}$")) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one letter, one number, and one special character (@, #, $, etc.).");
                } 
                else {
                    if (FileUtil.saveUser("AdminLogin.txt", username, password)) {
                        JOptionPane.showMessageDialog(null, "Registration Successful");
                        new AdminScreen();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration Failed");
                    }
                }
            }
        });

        // Back button functionality
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminScreen();
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new RegisterAdmin();
    }
}
