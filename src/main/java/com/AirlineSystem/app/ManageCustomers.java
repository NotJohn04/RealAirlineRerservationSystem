package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class ManageCustomers {

    private String[][] data;
    private JLabel selectedUserLabel;

    public ManageCustomers() {
        // Step 1: Read all lines from the file into a String array
        String[] d = FileUtil.readFile("CustomerLogin.txt");

        // Step 2: Create a 2D array to hold the table data (username, password, status)
        data = new String[d.length][3];  

        for (int i = 0; i < d.length; i++) {
            String line = d[i].trim();  
            String[] tokens = line.split(",", 3);  

            if (tokens.length == 3) {
                data[i][0] = tokens[0].trim();  
                data[i][1] = tokens[1].trim();  
                data[i][2] = tokens[2].trim();  
            } else {
                System.err.println("Invalid data format at line " + (i + 1) + ": " + d[i]);
            }
        }

        // Step 4: Define the column names
        String[] columnNames = {"Username", "Password", "Status"};

        // Step 5: Create a JTable with the data and column names
        JTable table = new JTable(data, columnNames);

        // Step 6: Set up a listener to select a user when a row is clicked
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String selectedUser = data[selectedRow][0];
                String status = data[selectedRow][2];

                selectedUserLabel.setText("Selected User: " + selectedUser + " (Status: " + status + ")");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Step 7: Set up the frame with the pink color theme
        JFrame frame = new JFrame();  
        frame.getContentPane().setBackground(new Color(0xF8BBD0));  // Pink background

        // Title section
        JLabel title = new JLabel("Manage Customers");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63)); 
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0xF8BBD0));
        bottomPanel.setLayout(new GridLayout(3, 1));

        selectedUserLabel = new JLabel("Select a user to unlock");
        bottomPanel.add(selectedUserLabel);

        JButton unlockButton = new JButton("Unlock Selected User");
        unlockButton.setEnabled(false);  
        bottomPanel.add(unlockButton);

        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton);
        
        backButton.addActionListener(e -> {
            frame.dispose();  
            new AdminScreen();  
        });

        // Enable/Disable the unlock button
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                if ("locked".equals(data[selectedRow][2])) {
                    unlockButton.setEnabled(true);
                } else {
                    unlockButton.setEnabled(false); 
                }
            }
        });

        // Action listener for unlocking
        unlockButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                data[selectedRow][2] = "unlocked";  
                table.setValueAt("unlocked", selectedRow, 2);  
                unlockButton.setEnabled(false);  

                FileUtil.updateUserStatus(data, "CustomerLogin.txt");  
                JOptionPane.showMessageDialog(frame, "User unlocked successfully!");
            }
        });

        // Add bottom panel to the frame
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        frame.setSize(600, 500);
        frame.setTitle("Manage Customers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);  
    }
}
