package com.AirlineSystem.app;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookFlight {
    private String username;
    private String[] flightDetails;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final int BUSINESS_SEATS = 5;
    private static final int ECONOMY_SEATS = 5;
    private GridBagConstraints gbc = new GridBagConstraints();

    public BookFlight(String username, String[] flightDetails) {
        this.username = username;
        this.flightDetails = flightDetails;

        initializeFrame();
    }

    private void initializeFrame() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setTitle("Book Flight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // Display flight details
        displayFlightDetails(frame);

        // Add seat selection buttons
        addSeatButtons(frame);

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

        for (int i = 1; i <= BUSINESS_SEATS; i++) {
            JButton button = createSeatButton("B" + i, "Business");
            businessPanel.add(button);
        }

        for (int i = 1; i <= ECONOMY_SEATS; i++) {
            JButton button = createSeatButton("E" + i, "Economy");
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
    }

    private JButton createSeatButton(String seatNumber, String seatClass) {
        JButton button = new JButton(seatNumber);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You selected " + seatClass + " seat " + seatNumber);
                // Add logic here to handle seat selection
            }
        });
        return button;
    }
}
