package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class ManageCustomers {

    private String[][] data;
    private JLabel selectedUserLabel; // To display the selected user for unlocking

    public ManageCustomers() {
        // Step 1: Read all lines from the file into a String array
        String[] d = FileUtil.readFile("CustomerLogin.txt");

        // Step 2: Create a 2D array to hold the table data (username, password, status)
        data = new String[d.length][3];  // Each row has 3 columns (username, password, status)

        // Step 3: Process each line, split it by commas, and store in the data array
        for (int i = 0; i < d.length; i++) {
            String line = d[i].trim();  // Remove trailing spaces or newline characters
            String[] tokens = line.split(",", 3);  // Split the string into 3 parts

            if (tokens.length == 3) {
                data[i][0] = tokens[0].trim();  // Username
                data[i][1] = tokens[1].trim();  // Password
                data[i][2] = tokens[2].trim();  // Status
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
                
                // Display the selected user and enable unlocking if locked
                selectedUserLabel.setText("Selected User: " + selectedUser + " (Status: " + status + ")");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Step 7: Set up the frame
        JFrame frame = new JFrame();  // Initialize the frame
        frame.add(scrollPane, BorderLayout.CENTER);  // Add scroll pane containing the table to the frame

        JLabel title = new JLabel("Manage Customers");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        // Set up the button and label area at the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));

        selectedUserLabel = new JLabel("Select a user to unlock");
        bottomPanel.add(selectedUserLabel);

        JButton unlockButton = new JButton("Unlock Selected User");
        unlockButton.setEnabled(false); // Disable the button initially
        bottomPanel.add(unlockButton);

        JButton backButton = new JButton("Back");

        bottomPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();  // Close the frame
            new AdminScreen();  // Open the main menu
        });

        // Enable the button if a locked user is selected
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                if ("locked".equals(data[selectedRow][2])) {
                    unlockButton.setEnabled(true);
                } else {
                    unlockButton.setEnabled(false); // Disable if the user is not locked
                }
            }
        });

        // Step 8: Action listener for unlocking the user
        unlockButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                data[selectedRow][2] = "unlocked";  // Update the status in the data array
                table.setValueAt("unlocked", selectedRow, 2);  // Update the table view
                unlockButton.setEnabled(false);  // Disable the button after unlocking

                // Optionally, save the change back to the file
                FileUtil.updateUserStatus(data, "CustomerLogin.txt");  // Persist changes
                JOptionPane.showMessageDialog(frame, "User unlocked successfully!");
            }
        });

        // Add bottom panel to the frame
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Set up the frame size and visibility
        frame.setSize(500, 400);
        frame.setTitle("Manage Customers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);  // Make the frame visible
    }
}
