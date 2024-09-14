package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class AdminScreen {

    JFrame frame = new JFrame();
    JLabel title = new JLabel("Admin Screen");
    JButton manageFlights = new JButton("Manage Flights");
    JButton manageCustomers = new JButton("Manage Customers");

    public AdminScreen() {

        title.setBounds(0, 0, 200, 50);
        title.setFont(new Font("MV Boli", Font.BOLD, 20));

        manageFlights.setBounds(100, 100, 200, 30);
        manageCustomers.setBounds(100, 150, 200, 30);

        frame.add(manageFlights);
        frame.add(manageCustomers);
        frame.add(title);
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
    }
    
}
