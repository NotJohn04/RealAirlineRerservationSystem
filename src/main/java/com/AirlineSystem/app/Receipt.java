package com.AirlineSystem.app;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Receipt {
    private String username;
    private String flightDetails;
    private String seatNumber;

    public Receipt(String username, String[] flightDetails, String seatNumber) {
        this.username = username;
        this.flightDetails = String.join(",", flightDetails);
        this.seatNumber = seatNumber;
    }

    public void displayReceipt() {

        String receipt = "Receipt for " + username + "\n" +
        "Flight Details: " + flightDetails + "\n" +
        "Seat Number: " + seatNumber;

        JFrame f = new JFrame();


        JLabel receiptLabel = new JLabel(receipt);

        receiptLabel.setBounds(10, 10, 450, 450);
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 16));
        f.add(receiptLabel);
        f.setSize(450, 450);
        f.setTitle("Receipt");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        JButton back = new JButton("Back");
        back.setBounds(10, 10, 100, 50);
        f.add(back);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LandingPage();
                f.dispose();
            }
        });

        


        
        // JLabel receiptLabel = new JLabel(receipt);
        // JOptionPane.showMessageDialog(null, receiptLabel, "Receipt", JOptionPane.INFORMATION_MESSAGE);

    }
    
}
