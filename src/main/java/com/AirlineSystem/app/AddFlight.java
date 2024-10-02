package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
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
    private String economyPrice;
    private String businessPrice;
    private List<JCheckBox> businessSeats;
    private List<JCheckBox> economySeats;

    public AddFlight() {
        // Create a new JFrame with increased size
        JFrame frame = new JFrame("Add Flight Details");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Increase padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Set a larger font for all components
        Font largeFont = new Font("Arial", Font.PLAIN, 16);
        UIManager.put("Label.font", largeFont);
        UIManager.put("TextField.font", largeFont);
        UIManager.put("Button.font", largeFont);
        UIManager.put("CheckBox.font", largeFont);

        // Create labels and text fields for each flight detail
        JLabel flightNumberLabel = new JLabel("Flight Number:");
        JTextField flightNumberField = new JTextField(20);

        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField(20);

        JLabel departureLabel = new JLabel("Departure:");
        JTextField departureField = new JTextField(20);

        JLabel departureDateLabel = new JLabel("Departure Date:");
        JTextField departureDateField = new JTextField(20);

        JLabel departureTimeLabel = new JLabel("Departure Time:");
        JTextField departureTimeField = new JTextField(20);

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        JTextField arrivalTimeField = new JTextField(20);

        JLabel flightTypeLabel = new JLabel("Flight Type:");
        JTextField flightTypeField = new JTextField(20);

        JLabel flightDurationLabel = new JLabel("Flight Duration:");
        JTextField flightDurationField = new JTextField(20);

        // JLabel seatClassLabel = new JLabel("Seat Class:");
        // JTextField seatClassField = new JTextField(20);

        JLabel economyPriceLabel = new JLabel("Economy Price:");
        JTextField economyPriceField = new JTextField(20);

        JLabel businessPriceLabel = new JLabel("Business Price:");
        JTextField businessPriceField = new JTextField(20);

        // Add a new field for flight status
        // JLabel flightStatusLabel = new JLabel("Flight Status:");
        // JTextField flightStatusField = new JTextField(20);

        // Create checkboxes for business and economy seats
        businessSeats = new ArrayList<>();
        economySeats = new ArrayList<>();

        JPanel seatPanel = new JPanel(new GridLayout(2, 6, 10, 10));
        seatPanel.add(new JLabel("Business Seats:"));
        for (int i = 1; i <= 5; i++) {
            JCheckBox seat = new JCheckBox("B" + i);
            businessSeats.add(seat);
            seatPanel.add(seat);
        }
        seatPanel.add(new JLabel("Economy Seats:"));
        for (int i = 1; i <= 5; i++) {
            JCheckBox seat = new JCheckBox("E" + i);
            economySeats.add(seat);
            seatPanel.add(seat);
        }
        

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(flightNumberLabel, gbc);
        gbc.gridx = 1;
        frame.add(flightNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(departureLabel, gbc);
        gbc.gridx = 1;
        frame.add(departureField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(destinationLabel, gbc);
        gbc.gridx = 1;
        frame.add(destinationField, gbc);

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

        // gbc.gridx = 0;
        // gbc.gridy = 9;
        // frame.add(seatClassLabel, gbc);
        // gbc.gridx = 1;
        // frame.add(seatClassField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        frame.add(economyPriceLabel, gbc);
        gbc.gridx = 1;
        frame.add(economyPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        frame.add(businessPriceLabel, gbc);
        gbc.gridx = 1;
        frame.add(businessPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        frame.add(seatPanel, gbc);

        // Adjust the position of other components
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        frame.add(new JLabel(), gbc);  // Empty label to fill the layout gap

        // Create buttons with increased size
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");
        saveButton.setPreferredSize(new Dimension(120, 40));
        backButton.setPreferredSize(new Dimension(120, 40));

        // Remove duplicate button creation and layout code
        gbc.gridx = 0;
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
                departure = departureField.getText();
                destination = destinationField.getText();
                departureDate = departureDateField.getText();
                departureTime = departureTimeField.getText();
                arrivalTime = arrivalTimeField.getText();
                flightType = flightTypeField.getText();
                flightDuration = flightDurationField.getText();
                economyPrice = economyPriceField.getText();
                businessPrice = businessPriceField.getText();
                // Get seat selections
                StringBuilder seatSelections = new StringBuilder();
                for (JCheckBox seat : businessSeats) {
                    seatSelections.append(seat.isSelected() ? "1," : "0,");
                }
                for (JCheckBox seat : economySeats) {
                    seatSelections.append(seat.isSelected() ? "1," : "0,");
                }
                // Remove the trailing comma
                if (seatSelections.length() > 0) {
                    seatSelections.setLength(seatSelections.length() - 1);
                }

                // Save the new flight details
                FileUtil.addFlightDetails(flightNumber, departure, destination, departureDate, departureTime, 
                    arrivalTime, flightType, flightDuration, economyPrice, businessPrice, seatSelections.toString());

                JOptionPane.showMessageDialog(frame, "Flight details added successfully!");

                new ManageFlights();
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