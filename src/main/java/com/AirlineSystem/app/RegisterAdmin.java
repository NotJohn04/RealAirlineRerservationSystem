package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class RegisterAdmin {

    JFrame frame = new JFrame();
    JLabel title = new JLabel("Register Admin");
    JLabel username = new JLabel("Username");
    JLabel password = new JLabel("Password");
    JTextField usernameInput = new JTextField();
    JTextField passwordInput = new JTextField();
    JButton registerButton = new JButton("Register Admin");
    JButton backButton = new JButton("Back");

    RegisterAdmin(){

        title.setBounds(20, 10, 200, 30); // Adjusted title position and width
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        username.setBounds(20, 50, 100, 30);
        frame.add(username);    

        usernameInput.setBounds(130, 50, 200, 30); // Adjusted username input position
        frame.add(usernameInput);

        password.setBounds(20, 90, 100, 30); // Adjusted password label position
        frame.add(password);

        passwordInput.setBounds(130, 90, 200, 30); // Adjusted password input position
        frame.add(passwordInput);

        registerButton.setBounds(130, 130, 100, 30); // Adjusted register button position
        frame.add(registerButton);

        backButton.setBounds(240, 130, 100, 30); // Adjusted back button position
        frame.add(backButton);

        frame.setTitle("Admin Register Screen");
        frame.add(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = passwordInput.getText();

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
                        new AdminScreen();  // Go back to the login screen
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration Failed");
                    }

                }
            }
        });

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
