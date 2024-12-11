package iit.lk.ticketingsystem.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketConfig {
    private int vendorCount;
    private int customerCount;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

}
