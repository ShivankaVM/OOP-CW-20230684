package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.TicketConfig;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public void save(TicketConfig ticketConfig) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("ticketConfig.json"), ticketConfig);
        System.out.println("Configuration saved to ticketConfig.json");
    }

    @Override
    public void loadConfigAndStart() {

    }
}
