package com.AirlineSystem.app;

import java.awt.Font;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchFlight {
    private JFrame frame;
    private JLabel title;

    public SearchFlight(String username) {

        String[] flightDetails = FileUtil.readFile("FlightsDetails.txt");
        // For debugging, print flight details
        for (String flight : flightDetails) {
            String[] details = flight.split(",");
            System.out.println(Arrays.toString(details));
        }

        // Create a new JFrame
        frame = new JFrame();
        frame.setSize(600, 600); // Increased size for better display
        frame.setTitle("Customer Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for precise control

        // Create GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Align components to the top-left
        gbc.ipadx = 20; // Increase padding between label and text field
        gbc.ipady = 10; // Increase padding between rows
        gbc.weightx = 1; // Allow horizontal expansion

        // Add welcome label
        JLabel welcome = new JLabel("Welcome " + username);
        welcome.setFont(new Font("MV Boli", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0; // No vertical expansion
        frame.add(welcome, gbc);

        // Flight details label
        JLabel flightDetailsLabel = new JLabel("Flight Details");
        flightDetailsLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridy = 1;
        frame.add(flightDetailsLabel, gbc);

        // Departure label and text field
        JLabel departureLabel = new JLabel("Departure:");
        departureLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridy = 2;
        frame.add(departureLabel, gbc);

        JTextField departureTextField = new JTextField(10);
        departureTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(departureTextField, gbc);

        // Destination label and text field
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(destinationLabel, gbc);

        JTextField destinationTextField = new JTextField(10);
        destinationTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(destinationTextField, gbc);

        // Date label and text field
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(dateLabel, gbc);

        JTextField dateTextField = new JTextField(10);
        dateTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(dateTextField, gbc);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Make the button span two columns
        gbc.weighty = 1; // Push everything to the top
        frame.add(searchButton, gbc);

        // Set visibility
        frame.setVisible(true);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String departure = departureTextField.getText();
                String destination = destinationTextField.getText();
                String date = dateTextField.getText();
                // Add your search logic here
                System.out.println("Departure: " + departure);
                System.out.println("Destination: " + destination);
                System.out.println("Date: " + date);

                // Search for flights using FileUtil
                String[] flights = FileUtil.searchFlights(departure, destination, date);
                // Display flights
                System.out.println(Arrays.toString(flights));
                new CustomerScreenFiltered(username, flights);
                frame.dispose();
            }
        });
    }

}