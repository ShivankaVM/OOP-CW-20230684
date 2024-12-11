package iit.lk.ticketingsystem.CLI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Customer implements Runnable {
    @Getter
    public final int customerId;// Unique ID of the customer
    @Getter
    public final int retrievalInterval;// Time interval (in milliseconds) between retrieval attempts
    private final TicketPool ticketPool;// Shared ticket pool for retrieving tickets

    @Override
    public void run() {
        try {
            while (true) {
                if (ticketPool.removeTicket(customerId)) {
                    // Log the date and time of successful ticket retrieval
                    System.out.printf("Customer %d successfully retrieved a ticket at %s.%n",
                            customerId, LocalDateTime.now());
                } else {
                    // Log the date and time of failed ticket retrieval
                    System.out.printf("Customer %d failed to retrieve a ticket at %s. No tickets available.%n",
                            customerId, LocalDateTime.now());
                }
                // Pause for the specified interval before attempting to retrieve again
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            // Log the interruption message for this customer
            System.out.printf("Customer %d interrupted. Stopping ticket retrieval.%n", customerId);
        }
    }

    @Override
    public String toString() {
        return String.format("Customer{id=%d, retrievalInterval=%d}", customerId, retrievalInterval);
    }
}

