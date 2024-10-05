package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

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

        // From Country label and text field
        JLabel fromCountryLabel = new JLabel("From Country:");
        fromCountryLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(fromCountryLabel, gbc);

        JTextField fromCountryTextField = new JTextField(10);
        fromCountryTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(fromCountryTextField, gbc);

        // To Country label and text field
        JLabel toCountryLabel = new JLabel("To Country:");
        toCountryLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(toCountryLabel, gbc);

        JTextField toCountryTextField = new JTextField(10);
        toCountryTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(toCountryTextField, gbc);

        // Date label
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD, e.g., 2024-09-25):");
        dateLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(dateLabel, gbc);

        JTextField dateTextField = new JTextField(10);
        dateTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(dateTextField, gbc);

        // From Time label
        JLabel fromTimeLabel = new JLabel("From Time (hh:mm AM/PM, e.g., 10:30 AM):");
        fromTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(fromTimeLabel, gbc);

        JTextField fromTimeTextField = new JTextField(10);
        fromTimeTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(fromTimeTextField, gbc);

        // To Time label
        JLabel toTimeLabel = new JLabel("To Time (hh:mm AM/PM, e.g., 06:30 PM):");
        toTimeLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(toTimeLabel, gbc);

        JTextField toTimeTextField = new JTextField(10);
        toTimeTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(toTimeTextField, gbc);

        // Domestic/International options
        JLabel typeLabel = new JLabel("Flight Type:");
        typeLabel.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 7;
        frame.add(typeLabel, gbc);

        String[] flightTypes = {"Any", "Domestic", "International"};
        JComboBox<String> flightTypeComboBox = new JComboBox<>(flightTypes);
        flightTypeComboBox.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        frame.add(flightTypeComboBox, gbc);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        frame.add(searchButton, gbc);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("MV Boli", Font.BOLD, 15));
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        frame.add(backButton, gbc);

        // Set visibility
        frame.setVisible(true);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fromCountry = fromCountryTextField.getText().trim();
                String toCountry = toCountryTextField.getText().trim();
                String date = dateTextField.getText().trim();
                String fromTime = fromTimeTextField.getText().trim();
                String toTime = toTimeTextField.getText().trim();
                String flightType = (String) flightTypeComboBox.getSelectedItem();

                // Search for flights using FileUtil
                String[] flights = FileUtil.searchFlights(fromCountry, toCountry, date, fromTime, toTime, flightType);

                if (flights.length > 0) {
                    new CustomerScreenFiltered(username, flights);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "No flights found matching your criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CustomerScreen(username);
            }
        });
    }

}