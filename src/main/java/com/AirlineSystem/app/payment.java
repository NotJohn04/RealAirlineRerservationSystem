package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class payment {

    private Color pinkTheme = new Color(0xF8BBD0); // Pink theme color
    private Color titleColor = new Color(0xe91e63); // Bold pink for titles

    public payment(String username, String[] flightDetails, String seat) {
        JFrame frame = new JFrame("Payment Confirmation");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(pinkTheme);

        // Create panels for different sections
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(pinkTheme);
        
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBackground(pinkTheme);

        JPanel pricePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        pricePanel.setBackground(pinkTheme);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(pinkTheme);

        // Header
        JLabel welcome = new JLabel("One Last Step, " + username);
        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(titleColor); // Pink title color
        headerPanel.add(welcome);

        // Details
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(titleColor, 2),
            "Flight Information",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 16),
            titleColor
        ));
        detailsPanel.add(new JLabel(
            "<html>" +
            "<div style='font-size: 14px;'>" +
            "Flight Number: " + flightDetails[0] + "<br>" +
            "From: " + flightDetails[1] + "<br>" +
            "To: " + flightDetails[2] + "<br>" +
            "Date: " + flightDetails[3] + "<br>" +
            "Time: " + flightDetails[4] + "<br>" +
            "Seat: " + seat +
            "</div>" +
            "</html>"
        ));

        boolean windowsSeat = false;
        int windowsSeatPrice = 0;

        if (seat.equals("B1") || seat.equals("B5") || seat.equals("E1") || seat.equals("E5")) {
            windowsSeatPrice += 100;
            windowsSeat = true;
        }

        // Calculate price and discount
        int count = calculateBookingCount(username);
        int priceDiscount = count * 100;
        int basePrice = seat.charAt(0) == 'B' ? Integer.parseInt(flightDetails[9]) : Integer.parseInt(flightDetails[8]);
        int totalPrice = basePrice - priceDiscount + windowsSeatPrice;

        // Price information
        pricePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(titleColor, 2),
            "Price Information",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 16),
            titleColor
        ));
        addLabelToPanel(pricePanel, "Class: " + (seat.charAt(0) == 'B' ? "Business" : "Economy"), 14);
        addLabelToPanel(pricePanel, "Base Price: RM" + basePrice, 14);
        addLabelToPanel(pricePanel, "Bookings Made: " + count, 14);
        addLabelToPanel(pricePanel, "Window Seat: " + windowsSeat, 14);
        addLabelToPanel(pricePanel, "Window Seat Price: RM" + windowsSeatPrice, 14);
        addLabelToPanel(pricePanel, "Price Discount: RM" + priceDiscount, 14);
        addLabelToPanel(pricePanel, "Total Price: RM" + totalPrice, 14);

        // Buttons
        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm Payment");
        styleButton(backButton);
        styleButton(confirmButton);

        backButton.addActionListener(e -> {
            new BookFlight(username, flightDetails);
            frame.dispose();
        });

        confirmButton.addActionListener(e -> {
            frame.dispose();
            showPaymentDetailsScreen(username, flightDetails, seat, totalPrice);
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(detailsPanel, BorderLayout.WEST);
        frame.add(pricePanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showPaymentDetailsScreen(String username, String[] flightDetails, String seat, int totalPrice) {
        JFrame paymentFrame = new JFrame("Enter Payment Details");
        paymentFrame.setSize(400, 400);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setLayout(new GridLayout(0, 1, 10, 10));
        paymentFrame.getContentPane().setBackground(pinkTheme);

        JTextField cardNumberField = new JTextField(16);
        JTextField expiryDateField = new JTextField(5);
        JTextField cvvField = new JTextField(3);

        paymentFrame.add(new JLabel("Card Number:"));
        paymentFrame.add(cardNumberField);
        paymentFrame.add(new JLabel("Expiry Date (MM/YY):"));
        paymentFrame.add(expiryDateField);
        paymentFrame.add(new JLabel("CVV:"));
        paymentFrame.add(cvvField);

        JButton payButton = new JButton("Pay");
        JButton backButton = new JButton("Back");
        styleButton(payButton);
        styleButton(backButton);

        payButton.addActionListener(ev -> {
            if (validatePaymentDetails(cardNumberField.getText(), expiryDateField.getText(), cvvField.getText())) {
                JOptionPane.showMessageDialog(paymentFrame, "Payment successful!");

                // Update seat availability in FlightsDetails.txt
                updateSeatAvailability(flightDetails[0], seat);

                // Save booking details to file
                String bookingDetails = String.join(",", username, flightDetails[0], flightDetails[2],
                        flightDetails[3], flightDetails[4], String.valueOf(totalPrice), cardNumberField.getText(), expiryDateField.getText(), seat, "normal");
                FileUtil.appendToFile("BookingDetails.txt", bookingDetails);

                paymentFrame.dispose();
                Receipt receipt_ = new Receipt(username);
                receipt_.displayReceipt();
            } else {
                JOptionPane.showMessageDialog(paymentFrame, "Invalid payment details. Please try again.");
            }
        });

        backButton.addActionListener(ev -> {
            paymentFrame.dispose();
            new payment(username, flightDetails, seat);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(pinkTheme);
        buttonPanel.add(payButton);
        buttonPanel.add(backButton);
        paymentFrame.add(buttonPanel);

        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setVisible(true);
    }

    private boolean validatePaymentDetails(String cardNumber, String expiryDate, String cvv) {
        return cardNumber.length() == 16 && expiryDate.matches("\\d{2}/\\d{2}") && cvv.length() == 3;
    }

    private int calculateBookingCount(String username) {
        int count = 0;
        String[] bookingDetails = FileUtil.readFile("BookingDetails.txt");
        for (String detail : bookingDetails) {
            String[] bookingDetail = detail.split(",");
            if (bookingDetail[0].equals(username)) {
                count++;
            }
        }
        return count;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 30));
        button.setBackground(new Color(255, 255, 255)); // White button for contrast
        button.setForeground(titleColor); // Pink text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(titleColor, 2));
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void addLabelToPanel(JPanel panel, String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(Color.BLACK); // Black text for clarity
        panel.add(label);
    }

    private void updateSeatAvailability(String flightNumber, String seat) {
        String[] flightsDetails = FileUtil.readFile("FlightsDetails.txt");
        StringBuilder updatedFlightsDetails = new StringBuilder();

        for (String flightDetail : flightsDetails) {
            String[] details = flightDetail.split(",");
            if (details[0].equals(flightNumber)) {
                int seatIndex = getSeatIndex(seat);
                if (seatIndex != -1) {
                    details[seatIndex] = "0";
                }
            }
            updatedFlightsDetails.append(String.join(",", details)).append("\n");
        }

        FileUtil.writeFile("FlightsDetails.txt", updatedFlightsDetails.toString().trim());
    }

    private int getSeatIndex(String seat) {
        char seatClass = seat.charAt(0);
        int seatNumber = Integer.parseInt(seat.substring(1));

        if (seatClass == 'B') {
            return 9 + seatNumber;
        } else if (seatClass == 'E') {
            return 14 + seatNumber;
        }
        return -1;
    }
}
