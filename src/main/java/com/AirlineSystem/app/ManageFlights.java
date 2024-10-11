package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ManageFlights {

    private String[][] data;
    private JLabel selectedFlightLabel;

    public ManageFlights() {
        String[] d = FileUtil.readFile("FlightsDetails.txt");

        data = new String[d.length][20];  // 20 columns
        for (int i = 0; i < d.length; i++) {
            String line = d[i].trim();
            String[] tokens = line.split(",", -1);

            if (tokens.length == 20) {
                System.arraycopy(tokens, 0, data[i], 0, 20);
            } else {
                System.err.println("Invalid data format at line " + (i + 1) + ": " + d[i]);
            }
        }

        String[] columnNames = {"Flight No", "Departure", "Destination", "Dep. Date", "Dep. Time", 
                                "Arr. Time", "Flight Type", "Duration", "Economy Price", "Business Price",
                                "B1", "B2", "B3", "B4", "B5", "E1", "E2", "E3", "E4", "E5"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        JTable table = new JTable(model);

        // Set grid lines for better visual separation
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // Set column alignment and widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // Flight No
        columnModel.getColumn(1).setPreferredWidth(120); // Departure
        columnModel.getColumn(2).setPreferredWidth(120); // Destination
        columnModel.getColumn(3).setPreferredWidth(100); // Dep. Date
        columnModel.getColumn(4).setPreferredWidth(80); // Dep. Time
        columnModel.getColumn(5).setPreferredWidth(80); // Arr. Time
        columnModel.getColumn(6).setPreferredWidth(150); // Flight Type
        columnModel.getColumn(7).setPreferredWidth(80); // Duration
        columnModel.getColumn(8).setPreferredWidth(100); // Economy Price
        columnModel.getColumn(9).setPreferredWidth(120); // Business Price
        for (int i = 10; i <= 19; i++) {
            columnModel.getColumn(i).setPreferredWidth(50); // Seat columns (B1, B2, E1, etc.)
        }

        // Set cell alignment to center for better readability
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        // Frame setup
        JFrame frame = new JFrame(); 
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(0xF8BBD0)); // Pink background

        // Title setup
        JLabel title = new JLabel("Manage Flights");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63)); // Dark pink title
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        // Button panel at the bottom
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(0xF8BBD0)); // Match background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around elements

        // Selected flight label
        selectedFlightLabel = new JLabel("Select a Flight to Edit");
        selectedFlightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        selectedFlightLabel.setForeground(new Color(0xe91e63)); // Dark pink text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bottomPanel.add(selectedFlightLabel, gbc);

        // Add flight button
        JButton addFlightButton = new JButton("Add Flight");
        addFlightButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addFlightButton.setBackground(new Color(0xe91e63)); // Dark pink background
        addFlightButton.setForeground(Color.WHITE);
        addFlightButton.setOpaque(true);
        addFlightButton.setBorderPainted(false);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        bottomPanel.add(addFlightButton, gbc);

        // Edit flight button
        JButton editButton = new JButton("Edit Selected Flight");
        editButton.setFont(new Font("Arial", Font.PLAIN, 16));
        editButton.setBackground(new Color(0xe91e63)); // Dark pink background
        editButton.setForeground(Color.WHITE);
        editButton.setOpaque(true);
        editButton.setBorderPainted(false);
        gbc.gridx = 1;
        bottomPanel.add(editButton, gbc);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(0xe91e63)); // Dark pink background
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        gbc.gridy = 2;
        gbc.gridx = 0;
        bottomPanel.add(backButton, gbc);

        // Add scroll pane with the table and bottom panel
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        frame.setSize(1200, 600);
        frame.setTitle("Manage Flights");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setVisible(true);

        // Action listeners
        addFlightButton.addActionListener(e -> {
            new AddFlight();
            frame.dispose();
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                new EditFlight(data[selectedRow]);
                frame.dispose();
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminScreen();
        });

        // Step 6: Set up a listener to update selected flight
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String selectedFlight = data[selectedRow][0];
                String destination = data[selectedRow][2];

                // Update the selected flight label
                selectedFlightLabel.setText("Selected Flight: " + selectedFlight + " (Destination: " + destination + ")");
            }
        });
    }
}
