package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageFlights {

    private String[][] data;
    private JLabel selectedFlightLabel;

    public ManageFlights() {
        String[] d = FileUtil.readFile("FlightsDetails.txt");

        data = new String[d.length][20];  // Update to 20 columns
        for (int i = 0; i < d.length; i++) {
            String line = d[i].trim();
            String[] tokens = line.split(",", -1);

            if (tokens.length == 20) {
                System.arraycopy(tokens, 0, data[i], 0, 20);
            } else {
                System.err.println("Invalid data format at line " + (i + 1) + ": " + d[i]);
            }
        }

        String[] columnNames = {"Flight Number", "Departure", "Destination", "Departure Date", "Departure Time", 
                                "Arrival Time", "Flight Type", "Flight Duration", "Economy Class Price", "Business Class Price",
                                "B1", "B2", "B3", "B4", "B5", "E1", "E2", "E3", "E4", "E5"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        // Step 6: Set up a listener to select a user when a row is clicked
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String selectedFlight = data[selectedRow][0];
                String Destination = data[selectedRow][2];
                
                // Display the selected user and enable unlocking if locked
                selectedFlightLabel.setText("Selected Flight: " + selectedFlight + " (Destination: " + Destination + ")");
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

        selectedFlightLabel = new JLabel("Select a Flight to Edit");
        bottomPanel.add(selectedFlightLabel);

        JButton editButton = new JButton("Select Flight to Edit");
        editButton.setEnabled(true); // Disable the button initially
        bottomPanel.add(editButton);
        bottomPanel.add(addFlightButton);
        bottomPanel.add(backButton);


        // Step 8: Action listener for unlocking the user
        editButton.addActionListener(e -> {
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
        frame.setSize(800, 600);
        
        frame.setTitle("Manage Flights");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);  // Make the frame visible
    }
}
