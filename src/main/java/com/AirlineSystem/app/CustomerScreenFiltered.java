package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

public class CustomerScreenFiltered {
    private JFrame frame;
    private JLabel title;
    private JPanel flightPanel;
    private String username;
    private String[] flightDetails;
    // Constants for consistent style
    private static final Font TITLE_FONT = new Font("MV Boli", Font.BOLD, 20);
    private static final Font DETAILS_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Dimension BUTTON_SIZE = new Dimension(100, 25);

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
        frame.setSize(600, 600); // Adjust the size
        frame.setTitle("Customer Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for precise control
    }

    // Add Welcome label and search button
    private void addWelcomeAndSearch() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Align components to the top-left
        gbc.ipadx = 20; // Increase padding between label and text field
        gbc.ipady = 10; // Increase padding between rows
        gbc.weightx = 1; // Allow horizontal expansion

        // Welcome Label
        JLabel welcome = new JLabel("Welcome " + username);
        welcome.setFont(TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0; // No vertical expansion
        frame.add(welcome, gbc);

        // Flight Details Label
        JLabel flightDetailsLabel = new JLabel("Flight Details");
        flightDetailsLabel.setFont(TITLE_FONT);
        gbc.gridy = 1;
        frame.add(flightDetailsLabel, gbc);

        // Search Button
        JButton searchButton = new JButton("Search Flights");
        searchButton.setPreferredSize(BUTTON_SIZE);
        gbc.gridy = 2;
        frame.add(searchButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(BUTTON_SIZE);
        gbc.gridy= 3;
        frame.add(logoutButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(BUTTON_SIZE);
        gbc.gridy= 4;
        frame.add(backButton, gbc);


        // Search Button Action Listener
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchFlight(username);
                frame.dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                frame.dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerScreen(username);
                frame.dispose();
            }
        });
    }

    // Display flight details dynamically in cards
    private void displayFlightDetails(String[] flightDetails) {
        flightPanel = new JPanel();
        flightPanel.setLayout(new BoxLayout(flightPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for better layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 6;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane scrollPane = new JScrollPane(flightPanel);
        frame.add(scrollPane, gbc);

        for (String flight : flightDetails) {
            String[] details = flight.split(",");

            JPanel flightCard = createFlightCard(details);
            flightPanel.add(flightCard);
        }
    }

    // Create a flight card for each flight
    private JPanel createFlightCard(String[] details) {
        JPanel flightCard = new JPanel();
        flightCard.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add border around the card
        flightCard.setLayout(new BoxLayout(flightCard, BoxLayout.Y_AXIS)); // Stack components vertically

        // Add flight details using HTML for formatted text
        JLabel flightInfo = new JLabel("<html><b>Flight Number:</b> " + details[0]
                + "<br/><b>Departure:</b> " + details[1]
                + "<br/><b>Destination:</b> " + details[2]
                + "<br/><b>Date:</b> " + details[3]
                + "<br/><b>Departure Time:</b> " + details[4]
                + "<br/><b>Arrival Time:</b> " + details[5]
                + "<br/><b>Status:</b> " + details[6]
                + "<br/><b>Flight Type:</b> " + details[7]
                + "<br/><b>Flight Duration:</b> " + details[8]
                + "<br/><b>Seat Number:</b> " + details[9]
                + "<br/><b>Seat Class:</b> " + details[10]
                + "<br/><b>Price:</b> " + details[11] + "</html>");
        flightInfo.setFont(DETAILS_FONT);

        // Add Book Button with action
        JButton bookButton = new JButton("Book " + details[0]);
        bookButton.setPreferredSize(BUTTON_SIZE);
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookFlight(username, details);
                frame.dispose();
            }
        });

        flightCard.add(flightInfo);
        flightCard.add(Box.createRigidArea(new Dimension(0, 5))); // Add some spacing
        flightCard.add(bookButton);

        return flightCard;
    }
}
