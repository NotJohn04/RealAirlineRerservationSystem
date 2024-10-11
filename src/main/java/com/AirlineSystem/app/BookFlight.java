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
    private Color PINK_THEME = new Color(0xF8BBD0);
    private Color LIGHT_GRAY = new Color(220, 220, 220);

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
        frame.getContentPane().setBackground(PINK_THEME);  // Apply the pink theme to the background
        frame.setLayout(new GridBagLayout());

        displayFlightDetails(frame);
        addSeatButtons(frame);
        addBackButton(frame);

        frame.setVisible(true);
    }

    private void displayFlightDetails(JFrame frame) {
        // Section: Flight Details Header
        JLabel flightDetailsLabel = new JLabel("Flight Details");
        flightDetailsLabel.setFont(TITLE_FONT);
        flightDetailsLabel.setForeground(new Color(0xe91e63));  // Pink theme for the title
        flightDetailsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, LIGHT_GRAY));  // Bottom border
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);  // Increased top padding
        frame.add(flightDetailsLabel, gbc);

        JLabel redButtonText = new JLabel("Red buttons: Seat taken");
        redButtonText.setFont(NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        frame.add(redButtonText, gbc);

        JLabel blueButtonText = new JLabel("Blue buttons: Window seat");
        blueButtonText.setFont(NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 20, 10);  // Increased bottom padding
        frame.add(blueButtonText, gbc);

        // Flight Details Grid
        String[] labels = {"Flight Number", "Departure", "Arrival", "Date", "Time"};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 3;  // Start from 3
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(NORMAL_FONT);
            frame.add(label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            JLabel detailLabel = new JLabel(flightDetails[i]);
            detailLabel.setFont(NORMAL_FONT);
            frame.add(detailLabel, gbc);
        }

        // Add divider between flight details and seat selection
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(500, 10));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        frame.add(separator, gbc);
    }

    private void addSeatButtons(JFrame frame) {
        // Seat Selection Header
        JLabel seatSelectionLabel = new JLabel("Select Your Seat");
        seatSelectionLabel.setFont(TITLE_FONT);
        seatSelectionLabel.setForeground(new Color(0xe91e63));  // Pink theme for header
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        frame.add(seatSelectionLabel, gbc);

        JPanel businessPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JPanel economyPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        businessPanel.setBackground(PINK_THEME);
        economyPanel.setBackground(PINK_THEME);

        for (int i = 0; i < 5; i++) {
            JButton businessButton = createSeatButton("B" + (i + 1), "Business", Integer.parseInt(flightDetails[10 + i]));
            businessPanel.add(businessButton);
        }

        for (int i = 0; i < 5; i++) {
            JButton economyButton = createSeatButton("E" + (i + 1), "Economy", Integer.parseInt(flightDetails[15 + i]));
            economyPanel.add(economyButton);
        }

        gbc.gridx = 0;
        gbc.gridy = 10;  
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 5, 10);
        JLabel businessLabel = new JLabel("Business Class Seats:");
        businessLabel.setFont(TITLE_FONT);
        businessLabel.setForeground(new Color(0xe91e63));  // Pink theme for labels
        frame.add(businessLabel, gbc);

        gbc.gridy = 11;
        frame.add(businessPanel, gbc);

        gbc.gridy = 12;
        JLabel economyLabel = new JLabel("Economy Class Seats:");
        economyLabel.setFont(TITLE_FONT);
        economyLabel.setForeground(new Color(0xe91e63));  // Pink theme for labels
        frame.add(economyLabel, gbc);

        gbc.gridy = 13;
        frame.add(economyPanel, gbc);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setEnabled(false);
        styleWhiteButton(confirmButton);  // Style the button with white background
        confirmButton.addActionListener(e -> confirmBooking());
        gbc.gridy = 14;
        frame.add(confirmButton, gbc);
    }

    private JButton createSeatButton(String seatNumber, String seatClass, int availability) {
        JButton button = new JButton(seatNumber);
        styleWhiteButton(button);  // Apply white background to seat buttons
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
            selectedSeatButton.setBackground(Color.WHITE);  // Reset to white when deselected
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
            }
        }
    }

    private void addBackButton(JFrame frame) {
        backButton = new JButton("Back");
        styleWhiteButton(backButton);  // Style the button with white background
        backButton.addActionListener(e -> goBack());
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);  // Increased bottom padding
        frame.add(backButton, gbc);
    }

    private void goBack() {
        frame.dispose();
        new CustomerScreen(username);
    }

    // Method to style buttons with white background and pink text
    private void styleWhiteButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0xe91e63));  // Pink text color
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));  // Adjust button size
    }
}
