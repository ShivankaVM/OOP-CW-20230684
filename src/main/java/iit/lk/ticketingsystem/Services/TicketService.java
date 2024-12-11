package iit.lk.ticketingsystem.Services;

import iit.lk.ticketingsystem.Models.TicketConfig;

import java.io.IOException;

public interface TicketService {
    void save(TicketConfig ticketConfig) throws IOException;

    void loadConfigAndStart();

}
