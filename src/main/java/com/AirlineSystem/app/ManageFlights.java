package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class ManageFlights {

    private String[][] data;
    private JLabel selectedUserLabel; // To display the selected user for unlocking

    public ManageFlights() {
        // Step 1: Read all lines from the file into a String array
        
        String[] d = FileUtil.readFile("FlightsDetails.txt");

        // Step 2: Create a 2D array to hold the table data (username, password, status)
        data = new String[d.length][12];  // Each row has 12 columns (Flight Number, Departure, Destination, Depature Date, Departure Time, Arrival Time, Flight Type, Flight Duration, Flight Status, Seat Number, Seat Class, Price)
        // Step 3: Process each line, split it by commas, and store in the data array
        for (int i = 0; i < d.length; i++) {
            String line = d[i].trim();  // Remove trailing spaces or newline characters
            String[] tokens = line.split(",", 12);  // Split the string into 12 parts

            if (tokens.length == 12) {
                data[i][0] = tokens[0].trim();  // Flight Number
                data[i][1] = tokens[1].trim();  // Departure
                data[i][2] = tokens[2].trim();  // Destination
                data[i][3] = tokens[3].trim();  // Depature Date
                data[i][4] = tokens[4].trim();  // Departure Time
                data[i][5] = tokens[5].trim();  // Arrival Time
                data[i][6] = tokens[6].trim();  // Flight Type
                data[i][7] = tokens[7].trim();  // Flight Duration
                data[i][8] = tokens[8].trim();  // Flight Status
                data[i][9] = tokens[9].trim();  // Seat Number
                data[i][10] = tokens[10].trim();  // Seat Class
                data[i][11] = tokens[11].trim();  // Prices
            } else {
                System.err.println("Invalid data format at line " + (i + 1) + ": " + d[i]);
            }
        }

        // Step 4: Define the column names
        String[] columnNames = {"Flight Number", "Departure", "Destination","Depature Date", "Departure Time", "Arrival Time", "Flight Type", "Flight Duration", "Flight Status", "Seat Number", "Seat Class", "Price"};

        // Step 5: Create a JTable with the data and column names
        JTable table = new JTable(data, columnNames);

        // Step 6: Set up a listener to select a user when a row is clicked
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String selectedUser = data[selectedRow][0];
                String status = data[selectedRow][2];
                
                // Display the selected user and enable unlocking if locked
                selectedUserLabel.setText("Selected Flight: " + selectedUser + " (Status: " + status + ")");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        // Step 7: Set up the frame
        JFrame frame = new JFrame();  // Initialize the frame
        frame.add(scrollPane, BorderLayout.CENTER);  // Add scroll pane containing the table to the frame

        JLabel title = new JLabel("Manage Flights");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        JButton addFlightButton = new JButton("Add Flight");


        addFlightButton.addActionListener(e -> {
            new AddFlight();
            frame.dispose();
        });
        // frame.add(addFlightButton, BorderLayout.WEST);

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminScreen();
        });
    

        // Set up the button and label area at the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 1));

        selectedUserLabel = new JLabel("Select a Flight to Edit");
        bottomPanel.add(selectedUserLabel);

        JButton EditButton = new JButton("Select Flight to Edit");
        EditButton.setEnabled(true); // Disable the button initially
        bottomPanel.add(EditButton);
        bottomPanel.add(addFlightButton);
        bottomPanel.add(backButton);



        // Enable the button if a locked user is selected
        // table.getSelectionModel().addListSelectionListener(event -> {
        //     if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
        //         int selectedRow = table.getSelectedRow();
        //         if ("locked".equals(data[selectedRow][2])) {
        //             unlockButton.setEnabled(true);
        //         } else {
        //             unlockButton.setEnabled(false); // Disable if the user is not locked
        //         }
        //     }
        // });

        // Step 8: Action listener for unlocking the user
        EditButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // data[selectedRow][2] = "unlocked";  // Update the status in the data array
                // table.setValueAt("unlocked", selectedRow, 2);  // Update the table view
                // EditButton.setEnabled(false);  // Disable the button after unlocking
                new EditFlight(data[selectedRow]);
                frame.dispose();

                // Optionally, save the change back to the file
            //     FileUtil.updateUserStatus(data, "CustomerLogin.txt");  // Persist changes
            //     JOptionPane.showMessageDialog(frame, "User unlocked successfully!");
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
