package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class Receipt {
    private JFrame frame;
    private String username;
    
    // Constants for consistent style
    private static final Font TITLE_FONT = new Font("MV Boli", Font.BOLD, 24);
    private static final Font DETAILS_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Dimension BUTTON_SIZE = new Dimension(160, 35);

    private Color pinkTheme = new Color(0xF8BBD0); // Soft pink background
    private Color cardBackground = Color.WHITE; // White background for cards
    private Color buttonBackground = new Color(0xe91e63); // Pink for buttons
    private Color buttonTextColor = Color.WHITE; // White text for buttons

    public Receipt(String username) {
        this.username = username;
        displayReceipt();
    }

    public void displayReceipt() {
        String[] bookingDetails = FileUtil.readFile("BookingDetails.txt");

        // Create the main frame
        frame = new JFrame("Bookings for " + username);
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(pinkTheme);
        frame.setLayout(new GridBagLayout());

        // Title
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Your Bookings:");
        title.setFont(TITLE_FONT);
        title.setForeground(buttonBackground);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        frame.add(title, gbc);

        // Create a panel to hold booking cards
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS)); // Stack cards vertically
        bookingPanel.setBackground(pinkTheme); // Pink background for the panel

        // Create a scroll pane for the booking cards
        JScrollPane scrollPane = new JScrollPane(bookingPanel);
        scrollPane.setPreferredSize(new Dimension(650, 450)); // Adjust scroll pane size
        scrollPane.setBorder(null); // Remove default scroll pane border
        scrollPane.getViewport().setBackground(pinkTheme); // Make scroll pane background pink

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 0, 0); // Add padding above the flight list
        frame.add(scrollPane, gbc);

        // Add booking cards dynamically
        for (String line : bookingDetails) {
            String[] details = line.split(",");
            if (details[0].equals(username)) {
                JPanel bookingCard = createBookingCard(details);
                bookingPanel.add(bookingCard);
                bookingPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between cards
            }
        }

        // Back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(pinkTheme);
        JButton backBtn = new JButton("Back");
        //styleButton(backBtn);
        buttonPanel.add(backBtn);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(buttonPanel, gbc);

        backBtn.addActionListener(e -> {
            new CustomerScreen(username);
            frame.dispose();
        });

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

 // Create a flight card for each booking
private JPanel createBookingCard(String[] details) {
    JPanel bookingCard = new JPanel(new GridBagLayout());  // Grid layout for booking card
    bookingCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Border around the card
    bookingCard.setBackground(Color.WHITE);  // White background for booking cards
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);  // Add padding inside the card
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTHWEST;

    // Flight Number and Date Section
    JLabel flightInfo = new JLabel("<html><div style='color:#e91e63;'><b>Flight Number:</b> " + details[1]
            + "<br/><b>Date:</b> " + details[4] + "</div></html>");
    flightInfo.setFont(DETAILS_FONT);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    bookingCard.add(flightInfo, gbc);

    // Departure and Destination Section
    JLabel routeInfo = new JLabel("<html><div style='color:#333;'><b>From:</b> " + details[2]
            + "<br/><b>To:</b> " + details[3] + "</div></html>");
    routeInfo.setFont(DETAILS_FONT);
    gbc.gridx = 1;
    gbc.gridy = 0;
    bookingCard.add(routeInfo, gbc);

    // Time and Seat Section
    JLabel timeSeatInfo = new JLabel("<html><div style='color:#333;'><b>Time:</b> " + details[5]
            + "<br/><b>Seat:</b> " + details[8] + "</div></html>");
    timeSeatInfo.setFont(DETAILS_FONT);
    gbc.gridx = 0;
    gbc.gridy = 1;
    bookingCard.add(timeSeatInfo, gbc);

    // Status Section
    JLabel statusInfo = new JLabel("<html><div style='color:#333;'><b>Status:</b> " + details[9] + "</div></html>");
    statusInfo.setFont(DETAILS_FONT);
    gbc.gridx = 1;
    gbc.gridy = 1;
    bookingCard.add(statusInfo, gbc);

    // Add View Receipt Button with action
    JButton viewReceiptButton = new JButton("View Receipt");
    // styleButton(viewReceiptButton);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;  // Center the button horizontally
    gbc.insets = new Insets(15, 0, 15, 0);  // Add padding for the button
    viewReceiptButton.addActionListener(e -> {
        showDetailedReceipt(details);
    });

    bookingCard.add(viewReceiptButton, gbc);

    return bookingCard;
}

    // Show detailed receipt in a new window
    private void showDetailedReceipt(String[] details) {
        String receipt = "<html><b>Receipt for " + username + "</b><br>" +
                "Flight: " + details[1] + "<br>" +
                "From: " + details[2] + "<br>" +
                "To: " + details[3] + "<br>" +
                "Date: " + details[4] + "<br>" +
                "Time: " + details[5] + "<br>" +
                "Total Paid: " + details[6] + "<br>" +
                "Seat: " + details[8] + "<br>" +
                "Status: " + details[9] + "</html>";

        JFrame receiptFrame = new JFrame("Detailed Receipt");
        receiptFrame.setSize(400, 300);
        receiptFrame.setLayout(new BorderLayout());
        receiptFrame.getContentPane().setBackground(pinkTheme);

        JLabel receiptLabel = new JLabel(receipt);
        receiptLabel.setFont(DETAILS_FONT);
        receiptLabel.setForeground(Color.BLACK);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setBackground(pinkTheme);
        receiptPanel.add(receiptLabel);

        // Refund Button
        JButton refundButton = new JButton("Request Refund");
        //styleButton(refundButton);
        refundButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button

        refundButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to request a refund for this flight?",
                    "Confirm Refund Request", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                requestRefund(details);
                JOptionPane.showMessageDialog(null, "Refund request submitted successfully. Please wait for admin approval.");
                receiptFrame.dispose(); // Close the receipt window
            }
        });

        receiptFrame.add(receiptPanel, BorderLayout.CENTER);
        receiptFrame.add(refundButton, BorderLayout.SOUTH);

        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setVisible(true);
    }

    // Request refund and update booking status
    private void requestRefund(String[] details) {
        FileUtil.updateBookingStatus(details[0], details[1], "pending");
    }

    // Method to style buttons uniformly
    private void styleButton(JButton button) {
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(buttonBackground); // Pink background for button
        button.setForeground(buttonTextColor); // White text color
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
