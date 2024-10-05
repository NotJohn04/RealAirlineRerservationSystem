package com.AirlineSystem.app;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageRefunds {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public ManageRefunds() {
        frame = new JFrame("Manage Refunds");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Username", "Flight ID", "Destination", "Date", "Time", "Price", "Card Number", "Expiry Date", "Seat", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve Refund");
        JButton backButton = new JButton("Back");
        buttonPanel.add(approveButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadPendingRefunds();

        approveButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String username = (String) model.getValueAt(selectedRow, 0);
                String flightId = (String) model.getValueAt(selectedRow, 1);
                String destination = (String) model.getValueAt(selectedRow, 2);
                String date = (String) model.getValueAt(selectedRow, 3);
                String time = (String) model.getValueAt(selectedRow, 4);
                String price = (String) model.getValueAt(selectedRow, 5);
                String cardNumber = (String) model.getValueAt(selectedRow, 6);
                String expiryDate = (String) model.getValueAt(selectedRow, 7);
                String seat = (String) model.getValueAt(selectedRow, 8);
                FileUtil.processRefund(username, flightId, destination, date, time, price, cardNumber, expiryDate, seat);
                // FileUtil.updateSeatAvailability(flightId, seat);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(frame, "Refund approved and processed.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a refund request to approve.");
            }
        });

        backButton.addActionListener(e -> {
            new AdminScreen();
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private void loadPendingRefunds() {
        String[] bookings = FileUtil.readFile("BookingDetails.txt");
        for (String booking : bookings) {
            String[] parts = booking.split(",");
            if (parts.length > 0 && parts[parts.length - 1].equals("pending")) {
                model.addRow(new String[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]});
            }
        }
    }

    public static void updateSeatAvailability(String flightNumber, String seatNumber) {
        String FILE_PATH = "../../../data/";
        File file = new File(FILE_PATH + "FlightsDetails.txt");
        StringBuilder fileContent = new StringBuilder();
        boolean flightFound = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] flightDetails = line.split(",");
                
                // Check if this is the correct flight
                if (flightDetails[0].equals(flightNumber)) {
                    flightFound = true;
                    String[] seatSelections = flightDetails[10].split(","); // Seat availability stored in 11th field

                    int seatIndex = getSeatIndex(seatNumber); // Get index of the seat
                    if (seatIndex != -1 && seatIndex < seatSelections.length) {
                        seatSelections[seatIndex] = "1"; // Mark seat as available (from "0" to "1")
                        flightDetails[10] = String.join(",", seatSelections); // Update seat info in flight details
                    }
                    line = String.join(",", flightDetails); // Rebuild the flight record
                }
                fileContent.append(line).append("\n"); // Append to content
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Step 3: Rewrite the FlightsDetails.txt file with updated seat availability
        if (flightFound) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Flight not found.");
        }
    }

    public static int getSeatIndex(String seatNumber) {
        if (seatNumber == null || seatNumber.isEmpty()) {
            System.err.println("Error: Empty or null seat number");
            return -1;
        }

        char seatClass = seatNumber.charAt(0); // First character is seat class (B or E)
        int seatNum;

        try {
            seatNum = Integer.parseInt(seatNumber.substring(1)); // Convert the rest into the seat number
        } catch (NumberFormatException e) {
            System.err.println("Error parsing seat number: " + seatNumber);
            return -1;
        }

        // Determine seat index based on seat class (business or economy)
        switch (seatClass) {
            case 'B': // Business class seats (B1 to B5)
                return seatNum - 1; // Index for business starts at 0
            case 'E': // Economy class seats (E1 to E5)
                return 5 + (seatNum - 1); // Index for economy starts at 5
            default:
                System.err.println("Invalid seat class: " + seatClass);
                return -1;
        }
    }       
}