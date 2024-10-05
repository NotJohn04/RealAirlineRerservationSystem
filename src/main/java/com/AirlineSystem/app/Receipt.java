package com.AirlineSystem.app;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;


public class Receipt {
    private String username;

    public Receipt(String username) {
        this.username = username;
        displayReceipt();
    }

    public void displayReceipt() {
        String[] bookingDetails = FileUtil.readFile("BookingDetails.txt");

        JFrame f = new JFrame();
        f.setLayout(null);
        f.setSize(500, 600);
        f.setTitle("Bookings for " + username);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int yOffset = 10;
        int cardCount = 0;

        for (String line : bookingDetails) {
            String[] details = line.split(",");
            if (details[0].equals(username)) {
                JLabel cardLabel = new JLabel("<html>" +
                    "Flight: " + details[1] + "<br>" +
                    "From: " + details[2] + "<br>" +
                    "To: " + details[3] + "<br>" +
                    "Date: " + details[4] + "<br>" +
                    "Time: " + details[5] + "<br>" +
                    "Seat: " + details[8] +
                    "</html>");
                cardLabel.setBounds(10, yOffset, 300, 120);
                if (details[9].equals("pending")) {
                    cardLabel.setBackground(Color.RED);
                    cardLabel.setForeground(Color.WHITE);
                    cardLabel.setOpaque(true);
                    cardLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    // cardLabel.setText("Pending");

                } 
                else if (details[9].equals("normal")) {
                    cardLabel.setBackground(Color.GREEN);
                }
                f.add(cardLabel);

                JButton viewReceiptBtn = new JButton("View Receipt");
                viewReceiptBtn.setBounds(320, yOffset, 150, 30);
                final String[] finalDetails = details;
                viewReceiptBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showDetailedReceipt(finalDetails);
                    }
                });
                f.add(viewReceiptBtn);

                yOffset += 130;
                cardCount++;
            }
        }

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10, yOffset, 100, 30);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CustomerScreen(username);
                f.dispose();
            }
        });
        f.add(backBtn);

        f.setSize(500, yOffset + 80);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void showDetailedReceipt(String[] details) {
        String receipt = "Receipt for " + username + "\n" +
            "Flight: " + details[1] + "\n" +
            "From: " + details[2] + "\n" +
            "To: " + details[3] + "\n" +
            "Date: " + details[4] + "\n" +
            "Time: " + details[5] + "\n" +
            "Total Paid: " + details[6] + "\n" +
            "Seat: " + details[8] + "\n" + 
            "Status: " + details[9];

        JFrame receiptFrame = new JFrame("Detailed Receipt");
        JLabel receiptLabel = new JLabel("<html>" + receipt.replace("\n", "<br>") + "</html>");

        JButton refundButton = new JButton("Refund");
        refundButton.setBounds(10, 210, 100, 30);

        refundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to request a refund for this flight?", 
                    "Confirm Refund Request", 
                    JOptionPane.YES_NO_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    requestRefund(details);
                    JOptionPane.showMessageDialog(null, "Refund request submitted successfully. Please wait for admin approval.");
                    receiptFrame.dispose(); // Close the receipt window
                }
            }
        });

        receiptLabel.setBounds(10, 10, 380, 200);
        receiptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        receiptFrame.add(receiptLabel);
        receiptFrame.add(refundButton);
        receiptFrame.setSize(400, 320);
        receiptFrame.setLayout(null);
        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setVisible(true);
    }

    private void requestRefund(String[] details) {
        // Update the booking status from "normal" to "pending"
        FileUtil.updateBookingStatus(details[0], details[1], "pending");
    }
    
}
