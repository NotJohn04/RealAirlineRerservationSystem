package com.AirlineSystem.app;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LandingPage();
        });
    }
}