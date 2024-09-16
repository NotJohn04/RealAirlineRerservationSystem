package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddFlight {
    private String flightNumber;
    private String departure;
    private String destination;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private String flightType;
    private String flightDuration;
    private String flightStatus;
    private String seatNumber;
    private String seatClass;
    private String price;

    public AddFlight() {
        // Create a new JFrame
        JFrame frame = new JFrame("Add Flight Details");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Add some padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create labels and text fields for each flight detail
        JLabel flightNumberLabel = new JLabel("Flight Number:");
        JTextField flightNumberField = new JTextField();

        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField();

        JLabel departureLabel = new JLabel("Departure:");
        JTextField departureField = new JTextField();

        JLabel departureDateLabel = new JLabel("Departure Date:");
        JTextField departureDateField = new JTextField();

        JLabel departureTimeLabel = new JLabel("Departure Time:");
        JTextField departureTimeField = new JTextField();

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        JTextField arrivalTimeField = new JTextField();

        JLabel flightTypeLabel = new JLabel("Flight Type:");
        JTextField flightTypeField = new JTextField();

        JLabel flightDurationLabel = new JLabel("Flight Duration:");
        JTextField flightDurationField = new JTextField();

        JLabel seatNumberLabel = new JLabel("Seat Number:");
        JTextField seatNumberField = new JTextField();

        JLabel seatClassLabel = new JLabel("Seat Class:");
        JTextField seatClassField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField();

        // Adding components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(flightNumberLabel, gbc);
        gbc.gridx = 1;
        frame.add(flightNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(destinationLabel, gbc);
        gbc.gridx = 1;
        frame.add(destinationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(departureLabel, gbc);
        gbc.gridx = 1;
        frame.add(departureField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(departureDateLabel, gbc);
        gbc.gridx = 1;
        frame.add(departureDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(departureTimeLabel, gbc);
        gbc.gridx = 1;
        frame.add(departureTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(arrivalTimeLabel, gbc);
        gbc.gridx = 1;
        frame.add(arrivalTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(flightTypeLabel, gbc);
        gbc.gridx = 1;
        frame.add(flightTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        frame.add(flightDurationLabel, gbc);
        gbc.gridx = 1;
        frame.add(flightDurationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        frame.add(seatNumberLabel, gbc);
        gbc.gridx = 1;
        frame.add(seatNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        frame.add(seatClassLabel, gbc);
        gbc.gridx = 1;
        frame.add(seatClassField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        frame.add(priceLabel, gbc);
        gbc.gridx = 1;
        frame.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        frame.add(statusLabel, gbc);
        gbc.gridx = 1;
        frame.add(statusField, gbc);

        // Create buttons
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        frame.add(new JLabel(), gbc);  // Empty label to fill the layout gap

        gbc.gridy = 13;
        gbc.gridwidth = 1;
        frame.add(saveButton, gbc);
        gbc.gridx = 1;
        frame.add(backButton, gbc);

        // Make the frame visible
        frame.setVisible(true);

        // Add action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the flight details based on user input
                flightNumber = flightNumberField.getText();
                destination = destinationField.getText();
                departure = departureField.getText();
                departureDate = departureDateField.getText();
                departureTime = departureTimeField.getText();
                arrivalTime = arrivalTimeField.getText();
                flightType = flightTypeField.getText();
                flightDuration = flightDurationField.getText();
                seatNumber = seatNumberField.getText();
                seatClass = seatClassField.getText();
                price = priceField.getText();
                flightStatus = statusField.getText();

                // Save the updated flight details (assumes FileUtil.addFlightDetails is implemented)
                FileUtil.addFlightDetails(flightNumber, destination, departure, departureDate, departureTime, arrivalTime, flightStatus, flightType, flightDuration, seatNumber, seatClass, price);
                JOptionPane.showMessageDialog(frame, "Flight details added successfully!");

                // Open ManageFlights screen (you must implement ManageFlights)
                new ManageFlights();

                // Close the current frame
                frame.dispose();
            }
        });

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManageFlights();
                frame.dispose();
            }
        });
    }
}
