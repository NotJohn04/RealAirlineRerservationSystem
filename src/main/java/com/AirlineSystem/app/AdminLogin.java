package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class AdminLogin {

    JFrame f = new JFrame();
    JLabel AdminLogin = new JLabel("Admin Login");
    JLabel title = new JLabel("Login");
    JLabel UserName = new JLabel("Username");
    JLabel Password = new JLabel("Password");

    JTextField InputUserName = new JTextField();
    JPasswordField InputPassword = new JPasswordField(); // Use JPasswordField for password input
    JButton Login = new JButton("Login");
    JButton Back = new JButton("Back");

    AdminLogin(){

        AdminLogin.setBounds(10, 30, 100, 30);
        AdminLogin.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(AdminLogin);

        title.setBounds(200, 30, 100, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(title);

        UserName.setBounds(100, 100, 100, 30);
        Password.setBounds(100, 150, 100, 30);
        InputUserName.setBounds(200, 100, 200, 30);
        InputPassword.setBounds(200, 150, 200, 30);
        Login.setBounds(150, 200, 100, 30);
        Back.setBounds(270, 200, 100, 30);

        f.add(UserName);
        f.add(InputUserName);
        f.add(Password);
        f.add(InputPassword);
        f.add(Login);
        f.add(Back);
        f.setTitle("Login Screen");
        f.setSize(500, 400);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        final int[] count = {0};

        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                f.dispose();
            }
        });

        // Add action listeners to buttons
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
                } 
                else {
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

    // The addActionListener method is not needed anymore
    // public void addActionListener(ActionListener e){
    //     if(e.getSource() == Login){
    //         System.out.println("Login button clicked");
    //     }
    //     else if(e.getSource() == Register){
    //         System.out.println("Register button clicked");
    //     }
    // }

    // This commented out method is also not needed
    // public void addActionListener(ActionListener e){
    //     if(e.getSource() == Register){
    //         System.out.println("Register button clicked");
    //     }
    // }
}
