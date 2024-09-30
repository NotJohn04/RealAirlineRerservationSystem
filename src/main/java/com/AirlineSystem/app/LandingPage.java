package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class LandingPage {
    JFrame f = new JFrame();
    JLabel title = new JLabel("Welcome to Our Airline Reservation System");
    JButton AdminLogin = new JButton("Admin Login");
    JButton CustomerLogin = new JButton("Customer Login");

    LandingPage(){
        title.setBounds(10, 30, 450, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        f.add(title);
        AdminLogin.setBounds(130, 100, 130, 30);
        CustomerLogin.setBounds(130, 150, 130, 30);
        f.add(AdminLogin);
        f.add(CustomerLogin);
        f.setSize(450, 450);
        f.setTitle("Landing Page");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

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
