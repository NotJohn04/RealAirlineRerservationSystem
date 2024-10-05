package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class BookFlight {
    private String username;
    private String[] flightDetails;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private GridBagConstraints gbc = new GridBagConstraints();
    private JButton selectedSeatButton;
    private JButton confirmButton;
    private JButton backButton;
    private JFrame frame;
    private Color VERY_LIGHT_BLUE = new Color(144, 224, 239);

    public BookFlight(String username, String[] flightDetails) {
        this.username = username;
        this.flightDetails = flightDetails;
        
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setSize(800, 800);
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
        gbc.insets = new Insets(20, 10, 10, 10);  // Increased top padding
        frame.add(flightDetailsLabel, gbc);

        JLabel redButtonText = new JLabel("if the buttons are red, it means the seat is taken");
        redButtonText.setFont(NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        frame.add(redButtonText, gbc);

        JLabel blueButtonText = new JLabel("if the buttons are blue, it means its a window seat");
        blueButtonText.setFont(NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 20, 10);  // Increased bottom padding
        frame.add(blueButtonText, gbc);
        // System.out.println(
        //     flightDetails[0] + " " + flightDetails[1] + " " + flightDetails[2] + " " + flightDetails[3] + " " + flightDetails[4] + " " + flightDetails[5] + " " + flightDetails[6] + " " + flightDetails[7] + " " + flightDetails[8] + " " + flightDetails[9] 
        
        // );

        String[] labels = {"Flight Number", "Departure", "Arrival", "Date", "Time"};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 3;  // Start from 3 instead of 1
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
        JPanel businessPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JPanel economyPanel = new JPanel(new GridLayout(1, 5, 5, 5));

        for (int i = 0; i < 5; i++) {
            JButton businessButton = createSeatButton("B" + (i + 1), "Business", Integer.parseInt(flightDetails[10 + i]));
            businessPanel.add(businessButton);
        }

        for (int i = 0; i < 5; i++) {
            JButton economyButton = createSeatButton("E" + (i + 1), "Economy", Integer.parseInt(flightDetails[15 + i]));
            economyPanel.add(economyButton);
        }

        gbc.gridx = 0;
        gbc.gridy = 8;  // Increased from 6
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 5, 10);
        frame.add(new JLabel("Business Class Seats:"), gbc);

        gbc.gridy = 9;  // Increased from 7
        gbc.insets = new Insets(5, 10, 20, 10);
        frame.add(businessPanel, gbc);

        gbc.gridy = 10;  // Increased from 8
        gbc.insets = new Insets(20, 10, 5, 10);
        frame.add(new JLabel("Economy Class Seats:"), gbc);

        gbc.gridy = 11;  // Increased from 9
        gbc.insets = new Insets(5, 10, 20, 10);
        frame.add(economyPanel, gbc);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> confirmBooking());
        gbc.gridy = 12;  // Increased from 10
        gbc.insets = new Insets(20, 10, 10, 10);
        frame.add(confirmButton, gbc);
    }

    private JButton createSeatButton(String seatNumber, String seatClass, int availability) {
        JButton button = new JButton(seatNumber);
        button.addActionListener(e -> handleSeatSelection((JButton) e.getSource(), seatNumber, seatClass));

        if (seatNumber.equals("B1") || seatNumber.equals("B5") || seatNumber.equals("E1") || seatNumber.equals("E5")) {
            button.setBackground(VERY_LIGHT_BLUE);
        }
        if (availability == 0) {
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

            if (confirm == JOptionPane.YES_OPTION)  {

                new payment(username, flightDetails, selectedSeatButton.getText());
                frame.dispose();




                // JOptionPane.showMessageDialog(null, "Booking confirmed for seat " + selectedSeatButton.getText());
                // selectedSeatButton.setEnabled(false);
                // selectedSeatButton.setBackground(Color.RED);

                
                
                // // Save booking details to file
                // String bookingDetails = String.join(",", username, flightDetails[0], flightDetails[1], flightDetails[2],
                //     flightDetails[3], flightDetails[4], selectedSeatButton.getText());
                // FileUtil.appendToFile("BookingDetails.txt", bookingDetails);
                // frame.dispose();
                // Receipt receipt_ = new Receipt(username, flightDetails, selectedSeatButton.getText());
                // receipt_.displayReceipt();

                // selectedSeatButton = null;
                // confirmButton.setEnabled(false);

            }
        }
    }

    private void addBackButton(JFrame frame) {
        backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());
        gbc.gridx = 0;
        gbc.gridy = 13;  // Increased from 11
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);  // Increased bottom padding
        frame.add(backButton, gbc);
    }

    private void goBack() {
        frame.dispose();
        new CustomerScreen(username);
    }
    
}