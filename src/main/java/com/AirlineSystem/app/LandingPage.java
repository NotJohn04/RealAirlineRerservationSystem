package com.AirlineSystem.app;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;



public class LandingPage {
    JFrame f = new JFrame();
    JLabel title = new JLabel("Landing Page");
    JButton AdminLogin = new JButton("Admin Login");
    JButton CustomerLogin = new JButton("Customer Login");

    LandingPage(){
        title.setBounds(200, 30, 100, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(title);
        AdminLogin.setBounds(100, 100, 100, 30);
        CustomerLogin.setBounds(100, 150, 100, 30);
        f.add(AdminLogin);
        f.add(CustomerLogin);
        f.setSize(420, 420);
        f.setTitle("Landing Page");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);

        AdminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
                f.dispose();
            }
        });

        CustomerLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerLogin();
                f.dispose();
            }
        });

    }

    
}
