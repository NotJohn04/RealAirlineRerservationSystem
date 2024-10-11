package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminScreen {

    JFrame frame = new JFrame();
    JLabel title = new JLabel("Admin Screen");
    JButton manageFlights = new JButton("Manage Flights");
    JButton manageCustomers = new JButton("Manage Customers");
    JButton registerAdmin = new JButton("Register Admin");
    JButton manageAdminButton = new JButton("Manage Admin");
    JButton LogoutButton = new JButton("Logout");
    JButton manageRefundsButton = new JButton("Manage Refunds");

    public AdminScreen() {

        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(0xF8BBD0)); // Set the pink background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.anchor = GridBagConstraints.CENTER; // Center the elements

        // Title styling
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63)); // Dark pink for title
        gbc.gridwidth = 2; // Title spans across two columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(title, gbc);

        // Button styling
        JButton[] buttons = {manageFlights, manageCustomers, registerAdmin, manageAdminButton, manageRefundsButton, LogoutButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBackground(new Color(0xe91e63)); // Dark pink background
            button.setForeground(Color.WHITE); // White text
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(200, 35)); // Consistent button size
        }

        // Add buttons to the layout
        gbc.gridwidth = 1; // Reset gridwidth for buttons
        gbc.gridy = 1;
        frame.add(manageFlights, gbc);

        gbc.gridy = 2;
        frame.add(manageCustomers, gbc);

        gbc.gridy = 3;
        frame.add(registerAdmin, gbc);

        gbc.gridy = 4;
        frame.add(manageAdminButton, gbc);

        gbc.gridy = 5;
        frame.add(manageRefundsButton, gbc);

        gbc.gridy = 6;
        frame.add(LogoutButton, gbc);

        // Frame settings
        frame.setTitle("Admin Screen");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Button actions
        manageCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageCustomers();
                frame.dispose();
            }
        });

        manageFlights.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageFlights();
                frame.dispose();
            }
        });

        registerAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterAdmin();
                frame.dispose();
            }
        });

        manageAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageAdmin();
                frame.dispose();
            }
        });

        manageRefundsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageRefunds();
                frame.dispose();
            }
        });

        LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new AdminScreen();
    }
}
