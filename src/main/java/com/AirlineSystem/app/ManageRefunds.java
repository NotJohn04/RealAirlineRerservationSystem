package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageRefunds {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public ManageRefunds() {
        frame = new JFrame("Manage Refunds");
        
        // Set pink background theme for the frame
        frame.getContentPane().setBackground(new Color(0xF8BBD0));  // Pink background color
        frame.setLayout(new BorderLayout());

        // Title setup
        JLabel title = new JLabel("Manage Refunds");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63));  // Pink color for title text
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel(new String[]{"Username", "Flight ID", "Destination", "Date", "Time", "Price", "Card Number", "Expiry Date", "Seat", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true); // Fills the viewport for better visibility
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout to center buttons
        buttonPanel.setBackground(new Color(0xF8BBD0));  // Pink background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around the buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton approveButton = new JButton("Approve Refund");
        approveButton.setBackground(new Color(0xe91e63));  // Pink button background
        approveButton.setForeground(Color.WHITE);  // White text
        approveButton.setOpaque(true);
        approveButton.setBorderPainted(false);
        approveButton.setFont(new Font("Arial", Font.BOLD, 16));  // Bold font for buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(approveButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(0xe91e63));  // Pink button background
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        buttonPanel.add(backButton, gbc);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load pending refunds from file
        loadPendingRefunds();

        // Approve Refund button action
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
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(frame, "Refund approved and processed.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a refund request to approve.");
            }
        });

        // Back button action
        backButton.addActionListener(e -> {
            new AdminScreen();
            frame.dispose();
        });

        // Frame setup
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Load pending refunds from the BookingDetails.txt file
    private void loadPendingRefunds() {
        String[] bookings = FileUtil.readFile("BookingDetails.txt");
        for (String booking : bookings) {
            String[] parts = booking.split(",");
            if (parts.length > 0 && parts[parts.length - 1].equals("pending")) {
                model.addRow(new String[]{parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]});
            }
        }
    }


}
