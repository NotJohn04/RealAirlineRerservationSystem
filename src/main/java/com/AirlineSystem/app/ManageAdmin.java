package com.AirlineSystem.app;

import java.awt.*;
import javax.swing.*;

public class ManageAdmin {

    private String[][] data;
    private JLabel selectedUserLabel; 

    public ManageAdmin() {
        String[] d = FileUtil.readFile("AdminLogin.txt");

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

        String[] columnNames = {"Username", "Password", "Status"};

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                String selectedUser = data[selectedRow][0];
                String status = data[selectedRow][2];
                selectedUserLabel.setText("Selected Admin: " + selectedUser + " (Status: " + status + ")");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame();  
        frame.getContentPane().setBackground(new Color(0xF8BBD0));  // Pink background color
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Admin");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0xe91e63));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title, BorderLayout.NORTH);

        frame.add(scrollPane, BorderLayout.CENTER);  

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0xF8BBD0));
        bottomPanel.setLayout(new GridLayout(3, 1));

        selectedUserLabel = new JLabel("Select an admin to unlock");
        selectedUserLabel.setForeground(new Color(0xe91e63));
        bottomPanel.add(selectedUserLabel);

        JButton unlockButton = new JButton("Unlock Selected Admin");    
        unlockButton.setEnabled(false);  
        bottomPanel.add(unlockButton);

        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();  
            new AdminScreen();  
        });

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

        unlockButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                data[selectedRow][2] = "unlocked";  
                table.setValueAt("unlocked", selectedRow, 2);  
                unlockButton.setEnabled(false);  

                FileUtil.updateUserStatus(data, "AdminLogin.txt");  
                JOptionPane.showMessageDialog(frame, "Admin unlocked successfully!");
            }
        });

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(600, 500);
        frame.setTitle("Manage Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);  
    }
}
