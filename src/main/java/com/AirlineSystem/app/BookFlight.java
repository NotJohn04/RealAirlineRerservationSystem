package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class BookFlight {
    private String username;
    private String[] flightDetails;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final int BUSINESS_SEATS = 5;
    private static final int ECONOMY_SEATS = 5;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JButton selectedSeatButton;
    private JButton confirmButton;
    private JButton backButton;
    private JFrame frame;
    private String[] seatAvailability;

    public BookFlight(String username, String[] flightDetails) {
        this.username = username;
        this.flightDetails = flightDetails;
        this.seatAvailability = flightDetails[11].split(",");  // Assuming seat availability is the 12th element

        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setTitle("Book Flight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        displayFlightDetails(frame);
        addSeatButtons(frame);
        addBackButton(frame);

        frame.setVisible(true);
    }

    private void displayFlightDetails(JFrame frame) {
        JLabel flightDetailsLabel = new JLabel("Flight Details");
        flightDetailsLabel.setFont(TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(flightDetailsLabel, gbc);

        String[] labels = {"Flight Number", "Departure", "Arrival", "Date", "Time"};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            frame.add(new JLabel(labels[i] + ":"), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel detailLabel = new JLabel(flightDetails[i]);
            detailLabel.setFont(NORMAL_FONT);
            frame.add(detailLabel, gbc);
        }
    }

    private void addSeatButtons(JFrame frame) {
        JPanel businessPanel = new JPanel(new GridLayout(1, BUSINESS_SEATS, 5, 5));
        JPanel economyPanel = new JPanel(new GridLayout(1, ECONOMY_SEATS, 5, 5));

        for (int i = 0; i < BUSINESS_SEATS; i++) {
            JButton button = createSeatButton("B" + (i + 1), "Business", i);
            businessPanel.add(button);
        }

        for (int i = 0; i < ECONOMY_SEATS; i++) {
            JButton button = createSeatButton("E" + (i + 1), "Economy", i + BUSINESS_SEATS);
            economyPanel.add(button);
        }

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 5, 10);
        frame.add(new JLabel("Business Class Seats:"), gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 10, 20, 10);
        frame.add(businessPanel, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 5, 10);
        frame.add(new JLabel("Economy Class Seats:"), gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(5, 10, 20, 10);
        frame.add(economyPanel, gbc);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> confirmBooking());
        gbc.gridy = 10;
        gbc.insets = new Insets(20, 10, 10, 10);
        frame.add(confirmButton, gbc);
    }

    private JButton createSeatButton(String seatNumber, String seatClass, int index) {
        JButton button = new JButton(seatNumber);
        button.addActionListener(e -> handleSeatSelection((JButton) e.getSource(), seatNumber, seatClass));
        
        if (index < seatAvailability.length && seatAvailability[index].equals("0")) {
            button.setEnabled(false);
            button.setBackground(Color.RED);
        }
        return button;
    }

    private void handleSeatSelection(JButton button, String seatNumber, String seatClass) {
        if (selectedSeatButton != null) {
            selectedSeatButton.setBackground(null);
        }
        selectedSeatButton = button;
        button.setBackground(Color.GREEN);
        confirmButton.setEnabled(true);
        JOptionPane.showMessageDialog(null, "You selected " + seatClass + " seat " + seatNumber);
    }

    private void confirmBooking() {
        if (selectedSeatButton != null) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to book this flight?\n" +
                "Flight Number: " + flightDetails[0] + "\n" +
                "From: " + flightDetails[1] + "\n" +
                "To: " + flightDetails[2] + "\n" +
                "Date: " + flightDetails[3] + "\n" +
                "Time: " + flightDetails[4] + "\n" +
                "Seat: " + selectedSeatButton.getText(),
                "Confirm Booking",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Booking confirmed for seat " + selectedSeatButton.getText());
                selectedSeatButton.setEnabled(false);
                selectedSeatButton.setBackground(Color.RED);
                
                // Save booking details to file
                String bookingDetails = String.join(",", username, flightDetails[0], flightDetails[1], flightDetails[2],
                    flightDetails[3], flightDetails[4], selectedSeatButton.getText());
                FileUtil.appendToFile("BookingDetails.txt", bookingDetails);
                frame.dispose();
                Receipt receipt_ = new Receipt(username, flightDetails, selectedSeatButton.getText());
                receipt_.displayReceipt();

                selectedSeatButton = null;
                confirmButton.setEnabled(false);

            }
        }
    }

    private void addBackButton(JFrame frame) {
        backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(backButton, gbc);
    }

    private void goBack() {
        frame.dispose();
        new CustomerScreen(username);

    }
    
}