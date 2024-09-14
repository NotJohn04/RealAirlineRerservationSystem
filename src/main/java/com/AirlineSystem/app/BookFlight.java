package com.AirlineSystem.app;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
public class BookFlight {
    private String username;
    private String[] flightDetails;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private GridBagConstraints gbc = new GridBagConstraints();

    public BookFlight(String username, String[] flightDetails) {
        this.username = username;
        this.flightDetails = flightDetails;

        initializeFrame();
    }

    private void initializeFrame() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setTitle("Book Flight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JLabel flightDetailsLabel = new JLabel("Flight Details");
        flightDetailsLabel.setFont(TITLE_FONT);
        gbc.gridy = 1;
        frame.add(flightDetailsLabel, gbc);

        frame.setVisible(true); // Make sure the frame is visible
        System.out.println(username);
        System.out.println(Arrays.toString(flightDetails));
    }

}
