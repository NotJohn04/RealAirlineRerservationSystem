package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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
        // this.flightNumber = flightDetails[0];   
        // this.departure = flightDetails[1];
        // this.destination = flightDetails[2];
        // this.departureDate = flightDetails[3];
        // this.departureTime = flightDetails[4];
        // this.arrivalTime = flightDetails[5];
        // this.flightType = flightDetails[6];
        // this.flightDuration = flightDetails[7]; 
        // this.flightStatus = flightDetails[8];
        // this.seatNumber = flightDetails[9];
        // this.seatClass = flightDetails[10];
        // this.price = flightDetails[11];

        // // Print flight details (for debugging purposes)
        // System.out.println("Flight Number: " + flightNumber);
        // System.out.println("Departure: " + departure);
        // System.out.println("Destination: " + destination);
        // System.out.println("Date: " + departureDate);
        // System.out.println("Time: " + departureTime);
        // System.out.println("Status: " + flightStatus);
        // System.out.println("Flight Type: " + flightType);
        // System.out.println("Flight Duration: " + flightDuration);
        // System.out.println("Seat Number: " + seatNumber);
        // System.out.println("Seat Class: " + seatClass);
        // System.out.println("Price: " + price);

        // Create a new JFrame
        JFrame frame = new JFrame("Edit Flight Details");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(13, 2));  // Update layout to accommodate all fields and Save button

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

        // Add components to the frame
        frame.add(flightNumberLabel);
        frame.add(flightNumberField);
        frame.add(destinationLabel);
        frame.add(destinationField);
        frame.add(departureLabel);
        frame.add(departureField);
        frame.add(departureDateLabel);
        frame.add(departureDateField);
        frame.add(departureTimeLabel);
        frame.add(departureTimeField);
        frame.add(arrivalTimeLabel);
        frame.add(arrivalTimeField);
        frame.add(flightTypeLabel);
        frame.add(flightTypeField);
        frame.add(flightDurationLabel);
        frame.add(flightDurationField);
        frame.add(seatNumberLabel);
        frame.add(seatNumberField);
        frame.add(seatClassLabel);
        frame.add(seatClassField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(statusLabel);
        frame.add(statusField);

        // Create the save button
        JButton saveButton = new JButton("Save");

        // Add the save button to the frame
        frame.add(new JLabel());  // Empty label to fill the layout gap
        frame.add(saveButton);

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

                // Save the updated flight details (assumes FileUtil.updateFlightDetails is implemented)
                FileUtil.addFlightDetails(flightNumber, destination, departure, departureDate, departureTime, arrivalTime, flightStatus, flightType, flightDuration, seatNumber, seatClass, price);
                JOptionPane.showMessageDialog(frame, "Flight details added successfully!");

                // Open ManageFlights screen (you must implement ManageFlights)
                new ManageFlights();

                // Close the current frame
                frame.dispose();
            }
        });
    }
}
