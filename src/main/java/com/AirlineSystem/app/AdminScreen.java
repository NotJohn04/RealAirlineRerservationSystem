package com.AirlineSystem.app;

import java.awt.Font;
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



        title.setBounds(0, 0, 200, 50);
        title.setFont(new Font("MV Boli", Font.BOLD, 20));

        manageFlights.setBounds(100, 100, 200, 30);
        manageCustomers.setBounds(100, 150, 200, 30);
        registerAdmin.setBounds(100, 200, 200, 30);
        manageAdminButton.setBounds(100, 250, 200, 30);
        LogoutButton.setBounds(100, 300, 200, 30);
        manageRefundsButton.setBounds(100, 350, 200, 30);
        frame.add(manageFlights);
        frame.add(manageCustomers);
        frame.add(manageAdminButton);
        frame.add(LogoutButton);
        frame.add(title);
        frame.add(registerAdmin);
        frame.add(manageRefundsButton);
        frame.setSize(420, 420);
        frame.setTitle("Admin Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);



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



        LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
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
    }
    
}
