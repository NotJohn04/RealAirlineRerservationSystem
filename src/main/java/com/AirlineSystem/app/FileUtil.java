package com.AirlineSystem.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileUtil {     
    private static final String FILE_PATH = "../../../data/";

    public static boolean validateLogin(String fileName, String username, String password) {
        File file = new File(FILE_PATH + fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUser(String fileName, String username) {
        File file = new File(FILE_PATH + fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return true;
                }
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveUser(String fileName, String username, String password) {
        // Create the directory if it does not exist
        File directory = new File(FILE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the data directory if it doesn't exist
        }

        // Use the data directory for file storage
        File file = new File(FILE_PATH + fileName);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(username + "," + password + "," + "unlocked" + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean lockUser(String fileName, String username) {
        File file = new File(FILE_PATH + fileName);
        StringBuilder fileContent = new StringBuilder();
        boolean userFound = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    credentials[2] = "locked";
                    userFound = true;
                }
                fileContent.append(String.join(",", credentials)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (userFound) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileContent.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void writeFile(String fileName, String content) {
        File file = new File(FILE_PATH + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addFlightDetails(String flightNumber, String departure, String destination, 
                                    String departureDate, String departureTime, String arrivalTime, 
                                    String flightDuration, String flightStatus, 
                                    String economyPrice, String businessPrice, String seatSelections) {
        String newFlight = String.join(",", flightNumber, departure, destination, departureDate, departureTime, 
                                   arrivalTime, flightDuration, flightStatus, economyPrice, businessPrice, seatSelections);
        appendToFile("FlightsDetails.txt", newFlight);
    }

    public static boolean deleteFlightDetails(String flightNumber) {
        File file = new File(FILE_PATH + "FlightsDetails.txt");
        StringBuilder fileContent = new StringBuilder();
        boolean flightFound = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                if (!details[0].equals(flightNumber)) {
                    fileContent.append(line).append("\n");
                } else {
                    flightFound = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (flightFound) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileContent.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static String[] readFile(String fileName) {
        List<String> lines = new ArrayList<>();  // Use a list to store lines

        // Create a File object with the full path
        File file = new File(FILE_PATH + fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Read each line of the file and add it to the list
            while ((line = reader.readLine()) != null) {
                lines.add(line);  // Add the whole line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the list to an array and return
        return lines.toArray(new String[0]);
    }  

    public static String[] searchFlights(String departure, String destination, String date) {
        List<String> matchingFlights = new ArrayList<>();
        File file = new File(FILE_PATH + "FlightsDetails.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");

                if (details[1].equals(departure) && details[2].equals(destination) && details[3].equals(date)) {
                    matchingFlights.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchingFlights.toArray(new String[0]);
    }


    public static boolean updateFlightDetails(String flightNumber, String departure, String destination,
                                              String departureDate, String departureTime, String arrivalTime,
                                              String flightDuration, String flightStatus,
                                              String economyPrice, String businessPrice, String seatSelections) {
        File file = new File(FILE_PATH + "FlightsDetails.txt");
        StringBuilder fileContent = new StringBuilder();
        boolean flightFound = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                if (details[0].equals(flightNumber)) {
                    // Update the flight details
                    String updatedFlight = String.join(",", flightNumber, departure, destination, departureDate,
                            departureTime, arrivalTime, flightDuration, flightStatus, economyPrice, businessPrice, seatSelections);
                    fileContent.append(updatedFlight).append("\n");
                    flightFound = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (flightFound) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(fileContent.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static void updateUserStatus(String[][] data, String fileName) {
        File file = new File(FILE_PATH + fileName);

        try (FileWriter writer = new FileWriter(file)) {
            for (String[] user : data) {
                writer.write(String.join(",", user) + "\n");  // Write each user to the file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static boolean checkLocked(String fileName, String username) {
        File file = new File(FILE_PATH + fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return credentials[2].equals("locked"); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void appendToFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(FILE_PATH + fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
