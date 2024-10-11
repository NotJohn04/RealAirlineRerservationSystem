package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class LandingPage {
    JFrame f = new JFrame();
    JLabel title = new JLabel("Welcome to Our Airline Reservation System");
    JButton AdminLogin = new JButton("Admin Login");
    JButton CustomerLogin = new JButton("Customer Login");

    LandingPage() {
        f.setLayout(new GridBagLayout());
        f.getContentPane().setBackground(new Color(0xF8BBD0)); // Restore the pink background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63));

        AdminLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        AdminLogin.setBackground(new Color(0xe91e63));
        AdminLogin.setForeground(Color.WHITE);
        AdminLogin.setPreferredSize(new Dimension(150, 35));
        AdminLogin.setOpaque(true);
        AdminLogin.setBorderPainted(false);

        CustomerLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        CustomerLogin.setBackground(new Color(0xe91e63));
        CustomerLogin.setForeground(Color.WHITE);
        CustomerLogin.setPreferredSize(new Dimension(150, 35));
        CustomerLogin.setOpaque(true);
        CustomerLogin.setBorderPainted(false);

        gbc.gridy = 0;
        f.add(title, gbc);
        gbc.gridy = 1;
        f.add(AdminLogin, gbc);
        gbc.gridy = 2;
        f.add(CustomerLogin, gbc);

        f.setSize(800, 600);
        f.setTitle("Landing Page");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        AdminLogin.addActionListener(e -> {
            new AdminLogin();
            f.dispose();
        });

        CustomerLogin.addActionListener(e -> {
            new CustomerLogin();
            f.dispose();
        });
    }

    public static void main(String[] args) {
        new LandingPage();
    }
}
