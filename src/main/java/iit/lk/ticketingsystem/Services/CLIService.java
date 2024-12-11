package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.CLI.Customer;
import iit.lk.ticketingsystem.CLI.TicketPool;
import iit.lk.ticketingsystem.CLI.Vendor;
import iit.lk.ticketingsystem.Controllers.WebSocketController;
import iit.lk.ticketingsystem.Models.TicketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class CLIService {
    private ExecutorService executorService;
    private TicketPool ticketPool;
    private TicketConfig config;
    private Thread vendorThread;
    private Thread customerThread1;
    private Thread customerThread2;

    @Autowired
    private WebSocketController webSocketController;

    private static final Logger logger = Logger.getLogger(CLIService.class.getName());

    public CLIService() {
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void initializeSystem() throws IOException {
        config = loadConfig();

        // Log initialization
        webSocketController.sendCliLog("Initializing Ticketing System at " + LocalDateTime.now());
        webSocketController.sendCliLog("Total Tickets: " + config.getTotalTickets());
        webSocketController.sendCliLog("Ticket Release Rate: " + config.getTicketReleaseRate() + " tickets/second");
        webSocketController.sendCliLog("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + " seconds/ticket");
        webSocketController.sendCliLog("Max Ticket Capacity: " + config.getMaxTicketCapacity());

        ticketPool = new TicketPool(config.getMaxTicketCapacity());
    }

    public String startSystem() {
        try {
            if (ticketPool == null) {
                initializeSystem();
            }

            // Create vendor and customers
            Vendor vendor = new Vendor(1, config.getTicketReleaseRate(), 1000, ticketPool) {
                @Override
                public void run() {
                    try {
                        while (running) {
                            boolean ticketsAdded = ticketPool.addTickets(getVendorId(), getTicketsPerRelease());
                            if (ticketsAdded) {
                                String logMessage = String.format("Vendor %d added %d tickets at %s",
                                        getVendorId(), getTicketsPerRelease(), LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            } else {
                                String logMessage = String.format("Vendor %d cannot add tickets. Max capacity reached at %s",
                                        getVendorId(), LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            }
                            Thread.sleep(getReleaseInterval());
                        }
                    } catch (InterruptedException e) {
                        webSocketController.sendCliLog("Vendor " + vendorId + " interrupted. Stopping ticket release.");
                    }
                }
            };

            Customer customer1 = new Customer(1, config.getCustomerRetrievalRate() * 1000, ticketPool) {
                @Override
                public void run() {
                    try {
                        while (true) {
                            if (ticketPool.removeTicket(customerId)) {
                                String logMessage = String.format("Customer %d successfully retrieved a ticket at %s",
                                        customerId, LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            } else {
                                String logMessage = String.format("Customer %d failed to retrieve a ticket at %s. No tickets available.",
                                        customerId, LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            }
                            Thread.sleep(retrievalInterval);
                        }
                    } catch (InterruptedException e) {
                        webSocketController.sendCliLog("Customer " + customerId + " interrupted. Stopping ticket retrieval.");
                    }
                }
            };

            Customer customer2 = new Customer(2, config.getCustomerRetrievalRate() * 1000, ticketPool) {
                @Override
                public void run() {
                    try {
                        while (true) {
                            if (ticketPool.removeTicket(customerId)) {
                                String logMessage = String.format("Customer %d successfully retrieved a ticket at %s",
                                        customerId, LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            } else {
                                String logMessage = String.format("Customer %d failed to retrieve a ticket at %s. No tickets available.",
                                        customerId, LocalDateTime.now());
                                webSocketController.sendCliLog(logMessage);
                            }
                            Thread.sleep(retrievalInterval);
                        }
                    } catch (InterruptedException e) {
                        webSocketController.sendCliLog("Customer " + customerId + " interrupted. Stopping ticket retrieval.");
                    }
                }
            };

            // Create threads
            vendorThread = new Thread(vendor);
            customerThread1 = new Thread(customer1);
            customerThread2 = new Thread(customer2);

            // Start threads
            vendorThread.start();
            Thread.sleep(100); // Allow vendor to populate tickets
            customerThread1.start();
            customerThread2.start();

            return "Ticketing System started successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error starting the system: " + e.getMessage();
        }
    }

    public String stopSystem() {
        try {
            if (vendorThread != null) vendorThread.interrupt();
            if (customerThread1 != null) customerThread1.interrupt();
            if (customerThread2 != null) customerThread2.interrupt();
            executorService.shutdownNow();

            webSocketController.sendCliLog("Ticketing System stopped at " + LocalDateTime.now());

            return "Ticketing System stopped successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error stopping the system: " + e.getMessage();
        }
    }

    public TicketConfig loadConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("ticketConfig.json"), TicketConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new TicketConfig(); // Return a default object in case of an error
        }
    }
}