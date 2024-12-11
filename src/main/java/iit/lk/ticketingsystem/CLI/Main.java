package iit.lk.ticketingsystem.CLI;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.TicketConfig;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Ask for configuration and populate TicketConfig object
        TicketConfig config = new TicketConfig();
        System.out.println("Welcome to the Real-Time Event Ticketing System");

        String choice;
        while (true) {

            System.out.println("Do you want to configure the system? (Y/N), select Y to use the system configurations");
            choice = scanner.next();
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")) {
                break;

            } else {
                System.out.println("Please enter a valid choice(Y or N)");
            }
        }
        if (choice.equalsIgnoreCase("y")) {
            int totalNumberOfTickets;
            int ticketReleaseRate;
            int customerRetrievalRate;
            int maximumTicketCapacity;


            while (true) {
                System.out.println("Enter the total number of tickets in the system: ");
                try {
                    totalNumberOfTickets = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number");
                    scanner.next(); // Clear the invalid input
                    continue;
                }

                if (totalNumberOfTickets > 0) {
                    break;
                } else {
                    System.out.println("Please enter a number greater than zero");
                }
            }

            config.setTotalTickets(totalNumberOfTickets); // Set the total tickets to be released


            while (true) {
                System.out.print("Enter ticket release rate (tickets per second): ");
                try {
                    ticketReleaseRate = scanner.nextInt();

                    if (ticketReleaseRate > 0) {
                        config.setTicketReleaseRate(ticketReleaseRate); // Set the valid ticket release rate
                        break;
                    } else {
                        System.out.println("Please enter a number greater than zero");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number");
                    scanner.next(); // Clear the invalid input
                }
            }


            while (true) {
                System.out.print("Enter customer retrieval rate (seconds per ticket): ");
                try {
                    customerRetrievalRate = scanner.nextInt();

                    if (customerRetrievalRate > 0) {
                        config.setCustomerRetrievalRate(customerRetrievalRate); // Set the valid customer retrieval rate
                        break;
                    } else {
                        System.out.println("Please enter a number greater than zero");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number");
                    scanner.next(); // Clear the invalid input
                }
            }



            while (true) {
                System.out.println("Enter the maximum capacity of the ticket pool: ");
                try {
                    maximumTicketCapacity = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number");
                    scanner.next(); // Clear the invalid input
                    continue;
                }

                if (maximumTicketCapacity > 0 && maximumTicketCapacity <= totalNumberOfTickets) {
                    break;
                } else {
                    System.out.println("Maximum ticket capacity must be greater than zero and less than or equal to the total number of tickets");
                }
            }

            // Set the validated maximum ticket capacity
            config.setMaxTicketCapacity(maximumTicketCapacity);


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("ticketConfig.json"), config);
            System.out.println("\nSystem configuration: " + config);// Display configuration summary

            // Create the ticket pool using the configuration
            TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Create and configure vendor and customers
            Vendor vendor = new Vendor(1, config.getTicketReleaseRate(), 1000, ticketPool);
            Customer customer1 = new Customer(1, config.getCustomerRetrievalRate() * 1000, ticketPool);// Multiply rate by 1000 to convert to milliseconds
            Customer customer2 = new Customer(2, config.getCustomerRetrievalRate() * 1000, ticketPool);

            // Create threads for vendor and customers
            Thread vendorThread = new Thread(vendor);
            Thread customerThread1 = new Thread(customer1);
            Thread customerThread2 = new Thread(customer2);

            // Start the vendor thread first
            vendorThread.start();

            // Delay to allow the vendor to add tickets
            try {
                Thread.sleep(1000); // 1- second delay to populate the ticket pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Start customer threads
            customerThread1.start();
            customerThread2.start();

            // Run the system for a limited time or until a condition is met
            try {
                Thread.sleep(10000);// Let the system run for 10 seconds
                vendorThread.interrupt();// Stop vendor thread
                customerThread1.interrupt();// Stop customer thread 1
                customerThread2.interrupt();// Stop customer thread 2
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Event Ticketing System finished.");
            scanner.close();
        }
    }
}

