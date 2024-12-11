package iit.lk.ticketingsystem.CLI;

import lombok.Getter;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final int maxTicketCapacity;
    @Getter
    private int currentTicketCount;
    private final ReentrantLock lock = new ReentrantLock();
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.currentTicketCount = 0; // Initially, no tickets are available
    }

    // Helper method to get the current timestamp as a formatted string
    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public boolean addTickets(int vendorId, int ticketsToAdd) {
        if (ticketsToAdd <= 0) {
            logger.warning(String.format("Vendor %d attempted to add invalid ticket count (%d) at %s.",
                    vendorId, ticketsToAdd, getCurrentTimestamp()));
            return false;
        }

        lock.lock();
        try {
            int ticketsAdded = 0;
            while (ticketsAdded < ticketsToAdd && currentTicketCount < maxTicketCapacity) {
                if (Thread.interrupted()) {
                    logger.info("Vendor " + vendorId + " interrupted. Stopping ticket addition.");
                    Thread.currentThread().interrupt(); // Preserve the interrupt status
                    return false;
                }
                currentTicketCount++;
                ticketsAdded++;
            }

            if (ticketsAdded > 0) {
                logger.info(String.format("Vendor %d added %d tickets at %s. Available tickets: %d",
                        vendorId, ticketsAdded, getCurrentTimestamp(), currentTicketCount));
            } else {
                logger.warning(String.format("Vendor %d could not add tickets at %s. Max capacity reached.",
                        vendorId, getCurrentTimestamp()));
            }
            return ticketsAdded == ticketsToAdd;
        } finally {
            lock.unlock();
        }
    }

    public boolean removeTicket(int customerId) {
        lock.lock();
        try {
            if (Thread.interrupted()) {
                logger.info("Customer " + customerId + " interrupted. Stopping ticket retrieval.");
                Thread.currentThread().interrupt(); // Preserve the interrupt status
                return false;
            }

            if (currentTicketCount > 0) {
                currentTicketCount--;
                logger.info(String.format("Customer %d purchased a ticket at %s. Available tickets: %d",
                        customerId, getCurrentTimestamp(), currentTicketCount));
                return true;
            } else {
                logger.warning(String.format("Customer %d failed to retrieve a ticket at %s. No tickets available.",
                        customerId, getCurrentTimestamp()));
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    // Make the getAvailableTickets method non-static
    public int getAvailableTickets() {
        return this.currentTicketCount;
    }
}