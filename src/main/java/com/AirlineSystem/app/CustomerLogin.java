package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class CustomerLogin {

    JFrame f = new JFrame();
    JLabel MainTitle = new JLabel("Customer Login");
    JLabel title = new JLabel("Login");
    JLabel UserName = new JLabel("Username");
    JLabel Password = new JLabel("Password");

    JTextField InputUserName = new JTextField();
    JPasswordField InputPassword = new JPasswordField(); // Use JPasswordField for password input
    JButton Login = new JButton("Login");
    JButton Register = new JButton("Register");
    JButton back = new JButton("Back");

    CustomerLogin(){

        MainTitle.setBounds(10, 30, 200, 30);
        MainTitle.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(MainTitle);

        title.setBounds(200, 30, 150, 40);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        f.add(title);

        UserName.setBounds(100, 100, 100, 30);
        Password.setBounds(100, 150, 100, 30);
        InputUserName.setBounds(200, 100, 200, 30);
        InputPassword.setBounds(200, 150, 200, 30);
        Login.setBounds(150, 200, 100, 30);
        Register.setBounds(270, 200, 100, 30);
        back.setBounds(350, 30, 100, 30);

        f.add(UserName);
        f.add(InputUserName);
        f.add(Password);
        f.add(InputPassword);
        f.add(Login);
        f.add(Register);
        f.add(back);
        f.setTitle("Login Screen");
        f.setSize(500, 400);
        f.setLayout(null);
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
                }
                else {
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
