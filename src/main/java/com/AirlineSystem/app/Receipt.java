package com.AirlineSystem.app;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Receipt {
    private String username;
    private String flightDetails;
    private String seatNumber;

    public Receipt(String username) {
        this.username = username;
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
                    "Seat: " + details[6] +
                    "</html>");
                cardLabel.setBounds(10, yOffset, 300, 120);
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
                new LandingPage();
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
            "Seat: " + details[6];

        JFrame receiptFrame = new JFrame("Detailed Receipt");
        JLabel receiptLabel = new JLabel("<html>" + receipt.replace("\n", "<br>") + "</html>");
        receiptLabel.setBounds(10, 10, 380, 200);
        receiptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        receiptFrame.add(receiptLabel);
        receiptFrame.setSize(400, 250);
        receiptFrame.setLayout(null);
        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setVisible(true);
    }
    
}
