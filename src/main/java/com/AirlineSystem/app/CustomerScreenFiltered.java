package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class CustomerScreenFiltered {
    private JFrame frame;
    private JLabel title;
    private JPanel flightPanel;
    private String username;
    private String[] flightDetails;

    // Constants for consistent style
    private static final Font TITLE_FONT = new Font("MV Boli", Font.BOLD, 24);
    private static final Font DETAILS_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Dimension BUTTON_SIZE = new Dimension(140, 30);

    public CustomerScreenFiltered(String username, String[] flightDetails) {
        this.username = username;
        this.flightDetails = flightDetails;

        // Initialize the frame
        initializeFrame();

        // Add Welcome and Flight Details UI
        addWelcomeAndSearch();

        // Add the flight details dynamically
        displayFlightDetails(flightDetails);

        // Set the frame visible
        frame.setVisible(true);
    }

    // Initialize the main JFrame
    private void initializeFrame() {
        frame = new JFrame();
        frame.setSize(700, 700); // Adjust the size
        frame.setTitle("Customer Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0xF8BBD0)); // Pink background
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for precise control
    }

    // Add Welcome label and search button
    private void addWelcomeAndSearch() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Welcome Label
        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(TITLE_FONT);
        welcome.setForeground(new Color(0xe91e63));  // Pink color for title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span title across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(welcome, gbc);

        // Flight Details Label
        JLabel flightDetailsLabel = new JLabel("Filtered Flight Results");
        flightDetailsLabel.setFont(TITLE_FONT);
        flightDetailsLabel.setForeground(new Color(0xe91e63));  // Pink color for title
        gbc.gridy = 1;
        frame.add(flightDetailsLabel, gbc);

        // Search Button
        JButton searchButton = new JButton("Search Flights");
        styleButton(searchButton);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;  // Align to the right
        frame.add(searchButton, gbc);

        // View Booking Button
        JButton viewBookingButton = new JButton("View Booking");
        styleButton(viewBookingButton);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;  // Align to the left
        frame.add(viewBookingButton, gbc);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;  // Center the logout button
        frame.add(logoutButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        gbc.gridy = 4;
        frame.add(backButton, gbc);

        // Button Actions
        searchButton.addActionListener(e -> {
            new SearchFlight(username);
            frame.dispose();
        });

        viewBookingButton.addActionListener(e -> {
            new Receipt(username);
            frame.dispose();
        });

        logoutButton.addActionListener(e -> {
            new LandingPage();
            frame.dispose();
        });

        backButton.addActionListener(e -> {
            new CustomerScreen(username);
            frame.dispose();
        });
    }

    // Display flight details dynamically in cards
    private void displayFlightDetails(String[] flightDetails) {
        flightPanel = new JPanel();
        flightPanel.setBackground(new Color(0xF8BBD0));  // Pink background for the flight panel
        flightPanel.setLayout(new BoxLayout(flightPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for better layout

        JScrollPane scrollPane = new JScrollPane(flightPanel);
        scrollPane.setPreferredSize(new Dimension(650, 450));  // Adjust scroll pane size
        scrollPane.setBorder(null);  // Remove default scroll pane border
        scrollPane.getViewport().setBackground(new Color(0xF8BBD0));  // Make scroll pane background pink

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 0, 0);  // Add padding above the flight list
        frame.add(scrollPane, gbc);

        for (String flight : flightDetails) {
            String[] details = flight.split(",");
            JPanel flightCard = createFlightCard(details);
            flightPanel.add(flightCard);
        }
    }

    // Create a flight card for each flight
    private JPanel createFlightCard(String[] details) {
        JPanel flightCard = new JPanel(new GridBagLayout());  // Grid layout for flight card
        flightCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Border around the card
        flightCard.setBackground(Color.WHITE);  // White background for flight cards
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // Add padding inside the card
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Flight Number and Date Section
        JLabel flightInfo = new JLabel("<html><div style='color:#e91e63;'><b>Flight Number:</b> " + details[0]
                + "<br/><b>Date:</b> " + details[3] + "</div></html>");
        flightInfo.setFont(DETAILS_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        flightCard.add(flightInfo, gbc);

        // Departure and Destination Section
        JLabel routeInfo = new JLabel("<html><div style='color:#333;'><b>Departure:</b> " + details[1]
                + "<br/><b>Destination:</b> " + details[2] + "</div></html>");
        routeInfo.setFont(DETAILS_FONT);
        gbc.gridx = 1;
        gbc.gridy = 0;
        flightCard.add(routeInfo, gbc);

        // Time Section (Departure and Arrival)
        JLabel timeInfo = new JLabel("<html><div style='color:#333;'><b>Departure Time:</b> " + details[4]
                + "<br/><b>Arrival Time:</b> " + details[5] + "</div></html>");
        timeInfo.setFont(DETAILS_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        flightCard.add(timeInfo, gbc);

        // Flight Type and Duration Section
        JLabel flightTypeInfo = new JLabel("<html><div style='color:#333;'><b>Flight Type:</b> " + details[6]
                + "<br/><b>Flight Duration:</b> " + details[7] + "</div></html>");
        flightTypeInfo.setFont(DETAILS_FONT);
        gbc.gridx = 1;
        gbc.gridy = 1;
        flightCard.add(flightTypeInfo, gbc);

        // Price Section (Economy and Business)
        JLabel priceInfo = new JLabel("<html><div style='color:#333;'><b>Economy Price:</b> " + details[8]
                + "<br/><b>Business Price:</b> " + details[9] + "</div></html>");
        priceInfo.setFont(DETAILS_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;  // Span across both columns
        flightCard.add(priceInfo, gbc);

        // Add Book Button with action
        JButton bookButton = new JButton("Book Flight");
        styleButton(bookButton);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        bookButton.addActionListener(e -> {
            new BookFlight(username, details);
            frame.dispose();
        });

        flightCard.add(bookButton, gbc);

        return flightCard;
    }

    // Method to style buttons uniformly
    private void styleButton(JButton button) {
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(new Color(0xe91e63));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
