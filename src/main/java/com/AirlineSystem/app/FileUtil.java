    package com.AirlineSystem.app;

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.File;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.time.format.DateTimeParseException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Locale;
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

        public static String[] searchFlights(String fromCountry, String toCountry, String date, String fromTime, String toTime, String flightType) {
            String[] allFlights = readFile("FlightsDetails.txt");
            List<String> matchingFlights = new ArrayList<>();

            LocalDate searchDate = date.isEmpty() ? null : LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);

            for (String flight : allFlights) {
                String[] details = flight.split(",");
                if (details.length < 12) continue; // Skip invalid entries

                boolean match = true;
                if (!fromCountry.isEmpty() && !fromCountry.equalsIgnoreCase(details[1])) match = false;
                if (!toCountry.isEmpty() && !toCountry.equalsIgnoreCase(details[2])) match = false;
                
                // Date check
                if (searchDate != null) {
                    LocalDate flightDate = LocalDate.parse(details[3]);
                    if (flightDate.isBefore(searchDate) || flightDate.isAfter(currentDate)) match = false;
                }

                // Time check
                if (!fromTime.isEmpty() && !toTime.isEmpty() && searchDate != null) {
                    try {
                        LocalTime flightTime = LocalTime.parse(details[4], timeFormatter);
                        LocalTime searchFromTime = LocalTime.parse(fromTime, timeFormatter);
                        LocalTime searchToTime = LocalTime.parse(toTime, timeFormatter);
                        if (flightTime.isBefore(searchFromTime) || flightTime.isAfter(searchToTime)) match = false;
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing time: " + e.getMessage());
                        match = false;
                    }
                }

                if (!flightType.equals("Any") && !flightType.equalsIgnoreCase(details[6])) match = false;

                if (match) {
                    matchingFlights.add(flight);
                }
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

        public static void updateBookingStatus(String username, String flightId, String newStatus) {
            List<String> lines = new ArrayList<>();
            try {
                lines = Files.readAllLines(Paths.get(FILE_PATH + "BookingDetails.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(username) && parts[1].equals(flightId)) {
                    parts[parts.length - 1] = newStatus;
                    lines.set(i, String.join(",", parts));
                    break;
                }
            }

            try {
                Files.write(Paths.get(FILE_PATH + "BookingDetails.txt"), lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void processRefund(String username, String flightNumber, String destination, String date, String time, String price, String cardNumber, String expiryDate, String seatNumber) {
            File file = new File(FILE_PATH + "BookingDetails.txt");
            StringBuilder fileContent = new StringBuilder();
            boolean bookingFound = false;

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts[0].equals(username) && parts[1].equals(flightNumber) && 
                        parts[2].equals(destination) && parts[3].equals(date) && 
                        parts[4].equals(time) && parts[5].equals(price) && 
                        parts[6].equals(cardNumber) && parts[7].equals(expiryDate) && 
                        parts[8].equals(seatNumber)) {
                        bookingFound = true;
                        // Skip this line (effectively deleting it)
                    } else {
                        fileContent.append(line).append("\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (bookingFound) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(fileContent.toString());
                    // Update seat availability
                    updateSeatAvailability(flightNumber, seatNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Booking not found.");
            }
        }

        public static void updateSeatAvailability(String flightNumber, String seatNumber) {
            File file = new File(FILE_PATH + "FlightsDetails.txt");
            StringBuilder fileContent = new StringBuilder();
            boolean flightFound = false;

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] flightDetails = line.split(",");
                    if (flightDetails[0].equals(flightNumber)) {
                        flightFound = true;
                        System.out.println(seatNumber);
                        // String[] seatSelections = {flightDetails[10]};
                        int seatIndex = getSeatIndex(seatNumber);
                        System.out.println(seatIndex);
                        // System.out.println(seatSelections.length);
                        if (seatIndex != -1) {
                            System.out.println("ok");
                            flightDetails[seatIndex] = "1"; // Set seat to available
                            // flightDetails[10] = String.join(",", seatSelections);
                        }
                        line = String.join(",", flightDetails);
                    }
                    fileContent.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (flightFound) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(fileContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Flight not found.");
            }
        }

        public static int getSeatIndex(String seatNumber) {
            if (seatNumber == null || seatNumber.isEmpty()) {
                System.err.println("Error: Empty or null seat number");
                return -1;
            }

            char seatClass = seatNumber.charAt(0);
            System.out.println(seatClass);
            int seatNum;

            try {
                seatNum = Integer.parseInt(seatNumber.substring(1));
                System.out.println(seatNum);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing seat number: " + seatNumber);
                return -1;
            }

            switch (seatClass) {
                case 'B' -> {
                    // Business class seats (B1 to B5)
                    return 9 + seatNum;
                }
                case 'E' -> {
                    // Economy class seats (E1 to E5)
                    return 14 + seatNum;
                }
                default -> {
                    System.err.println("Invalid seat class: " + seatClass);
                    return -1;
                }
            }
        }
    }
