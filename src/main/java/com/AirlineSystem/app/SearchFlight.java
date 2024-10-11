package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class SearchFlight {
    private JFrame frame;
    private JLabel title;

    public SearchFlight(String username) {

        String[] flightDetails = FileUtil.readFile("FlightsDetails.txt");

        // Create a new JFrame
        frame = new JFrame();
        frame.setSize(600, 600); // Adjust the size for better display
        frame.setTitle("Search Flight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for precise control
        frame.getContentPane().setBackground(new Color(0xF8BBD0)); // Set pink background

        // Create GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.weightx = 1;

        // Add welcome label
        JLabel welcome = new JLabel("Welcome " + username);
        welcome.setFont(new Font("MV Boli", Font.BOLD, 20));
        welcome.setForeground(new Color(0xe91e63)); // Set font color to pink
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(welcome, gbc);

        // Flight details label
        JLabel flightDetailsLabel = new JLabel("Flight Search");
        flightDetailsLabel.setFont(new Font("MV Boli", Font.BOLD, 18));
        flightDetailsLabel.setForeground(new Color(0xe91e63));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(flightDetailsLabel, gbc);

        // From Country label and text field
        JLabel fromCountryLabel = new JLabel("From Country:");
        fromCountryLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        fromCountryLabel.setForeground(new Color(0xe91e63));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(fromCountryLabel, gbc);

        JTextField fromCountryTextField = new JTextField(10);
        fromCountryTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(fromCountryTextField, gbc);

        // To Country label and text field
        JLabel toCountryLabel = new JLabel("To Country:");
        toCountryLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        toCountryLabel.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(toCountryLabel, gbc);

        JTextField toCountryTextField = new JTextField(10);
        toCountryTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(toCountryTextField, gbc);

        // Date label and text field
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        dateLabel.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(dateLabel, gbc);

        JTextField dateTextField = new JTextField(10);
        dateTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(dateTextField, gbc);

        // From Time label and text field
        JLabel fromTimeLabel = new JLabel("From Time (hh:mm AM/PM):");
        fromTimeLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        fromTimeLabel.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(fromTimeLabel, gbc);

        JTextField fromTimeTextField = new JTextField(10);
        fromTimeTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(fromTimeTextField, gbc);

        // To Time label and text field
        JLabel toTimeLabel = new JLabel("To Time (hh:mm AM/PM):");
        toTimeLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        toTimeLabel.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(toTimeLabel, gbc);

        JTextField toTimeTextField = new JTextField(10);
        toTimeTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(toTimeTextField, gbc);

        // Flight type dropdown
        JLabel typeLabel = new JLabel("Flight Type:");
        typeLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
        typeLabel.setForeground(new Color(0xe91e63));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(typeLabel, gbc);

        String[] flightTypes = {"Any", "Domestic", "International"};
        JComboBox<String> flightTypeComboBox = new JComboBox<>(flightTypes);
        flightTypeComboBox.setFont(new Font("MV Boli", Font.PLAIN, 15));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(flightTypeComboBox, gbc);

        // Search button
        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(searchButton, gbc);

        // Back button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        gbc.gridy = 9;
        frame.add(backButton, gbc);

        // Set visibility
        frame.setVisible(true);

        // Action listeners for buttons
        searchButton.addActionListener(e -> {
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
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new CustomerScreen(username);
        });
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 30));
        button.setBackground(new Color(0xe91e63)); // Pink background for buttons
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
