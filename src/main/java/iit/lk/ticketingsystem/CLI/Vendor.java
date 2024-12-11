package iit.lk.ticketingsystem.CLI;

import lombok.Getter;

import java.time.LocalDateTime;

public class Vendor implements Runnable {
    @Getter
    public final int vendorId; // Unique ID of the vendor
    @Getter
    private final int ticketsPerRelease;// Number of tickets to release per interval
    @Getter
    private final int releaseInterval;// Time interval (in milliseconds) between releases
    private final TicketPool ticketPool;// Shared ticket pool for adding tickets
    public volatile boolean running = true;// Flag to control thread execution

    // Constructor to initialize the vendor
    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        if (ticketsPerRelease <= 0 || releaseInterval <= 0) {
            throw new IllegalArgumentException("Tickets per release and interval must be positive.");
        }
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (running) { // Loop while the thread is running
                boolean ticketsAdded = ticketPool.addTickets(vendorId, ticketsPerRelease);

                if (ticketsAdded) {
                    // Log only if tickets were added successfully
                    System.out.printf("Vendor %d added %d tickets at %s%n",
                            vendorId, ticketsPerRelease, LocalDateTime.now());
                } else {
                    // Log that tickets cannot be added if capacity is reached
                    System.out.printf("Vendor %d cannot add tickets. Max capacity reached at %s%n",
                            vendorId, LocalDateTime.now());
                }

                // Pause for the specified interval
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            System.out.printf("Vendor %d interrupted. Stopping ticket release.%n", vendorId);
        }
    }


    // Method to stop the vendor thread gracefully
    public void stop() {
        running = false;
    }

    @Override
    public String toString() {
        return String.format("Vendor{id=%d, ticketsPerRelease=%d, releaseInterval=%d}",
                vendorId, ticketsPerRelease, releaseInterval);
    }
}
