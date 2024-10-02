package com.AirlineSystem.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class EditFlight {
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

    public EditFlight(String[] flightDetails) {
        // Initialize flight details
        this.flightNumber = flightDetails[0];
        this.departure = flightDetails[1];
        this.destination = flightDetails[2];
        this.departureDate = flightDetails[3];
        this.departureTime = flightDetails[4];
        this.arrivalTime = flightDetails[5];
        this.flightType = flightDetails[6];
        this.flightDuration = flightDetails[7];
        this.economyPrice = flightDetails[8];
        this.businessPrice = flightDetails[9];


        // Create a new JFrame with increased size
        JFrame frame = new JFrame("Edit Flight Details");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Set a larger font for all components
        Font largeFont = new Font("Arial", Font.PLAIN, 16);
        UIManager.put("Label.font", largeFont);
        UIManager.put("TextField.font", largeFont);
        UIManager.put("Button.font", largeFont);
        UIManager.put("CheckBox.font", largeFont);

        // Create labels and text fields for each flight detail
        JLabel flightNumberLabel = new JLabel("Flight Number:");
        JTextField flightNumberField = new JTextField(flightNumber, 20);

        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField(destination, 20);

        JLabel departureLabel = new JLabel("Departure:");
        JTextField departureField = new JTextField(departure, 20);

        JLabel departureDateLabel = new JLabel("Departure Date:");
        JTextField departureDateField = new JTextField(departureDate, 20);

        JLabel departureTimeLabel = new JLabel("Departure Time:");
        JTextField departureTimeField = new JTextField(departureTime, 20);

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        JTextField arrivalTimeField = new JTextField(arrivalTime, 20);

        JLabel flightTypeLabel = new JLabel("Flight Type:");
        JTextField flightTypeField = new JTextField(flightType, 20);

        JLabel flightDurationLabel = new JLabel("Flight Duration:");
        JTextField flightDurationField = new JTextField(flightDuration, 20);

        JLabel economyPriceLabel = new JLabel("Economy Price:");
        JTextField economyPriceField = new JTextField(economyPrice, 20);

        JLabel businessPriceLabel = new JLabel("Business Price:");
        JTextField businessPriceField = new JTextField(businessPrice, 20);



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

        // Set seat selections based on flightDetails[9]
        String[] seatSelections = flightDetails[9].split(",");
        for (int i = 0; i < seatSelections.length && i < businessSeats.size() + economySeats.size(); i++) {
            if (i < businessSeats.size()) {
                businessSeats.get(i).setSelected(seatSelections[i].equals("1"));
            } else {
                economySeats.get(i - businessSeats.size()).setSelected(seatSelections[i].equals("1"));
            }
        }

        // Add components to the frame
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
        frame.add(economyPriceLabel, gbc);
        gbc.gridx = 1;
        frame.add(economyPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        frame.add(businessPriceLabel, gbc);
        gbc.gridx = 1;
        frame.add(businessPriceField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        frame.add(seatPanel, gbc);

        // Create buttons with increased size
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        saveButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.setPreferredSize(new Dimension(120, 40));
        backButton.setPreferredSize(new Dimension(120, 40));

        // Add buttons to the frame
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 1;
        frame.add(saveButton, gbc);
        gbc.gridx = 1;
        frame.add(deleteButton, gbc);
        gbc.gridx = 2;
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

                // Save the updated flight details
                FileUtil.updateFlightDetails(flightNumber, departure, destination, departureDate, departureTime, 
                                             arrivalTime, flightType, flightDuration, 
                                             economyPrice, businessPrice, seatSelections.toString());
                JOptionPane.showMessageDialog(frame, "Flight details updated successfully!");

                new ManageFlights();
                frame.dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileUtil.deleteFlightDetails(flightNumber);
                JOptionPane.showMessageDialog(frame, "Flight details deleted successfully!");
                new ManageFlights();
                frame.dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageFlights();
                frame.dispose();
            }
        });
    }
}