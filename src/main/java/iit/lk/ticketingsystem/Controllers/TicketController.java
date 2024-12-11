package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.CLI.TicketPool;
import iit.lk.ticketingsystem.Models.TicketConfig;
import iit.lk.ticketingsystem.Services.CLIService;
import iit.lk.ticketingsystem.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    private Thread ticketThread;
    private static final Logger logger = Logger.getLogger(TicketController.class.getName());

    // Atomic counters for thread-safe operations
    private final AtomicInteger vendorCount = new AtomicInteger(0);
    private final AtomicInteger customerCount = new AtomicInteger(0);

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody TicketConfig ticketConfig) {
        Map<String, String> response = new HashMap<>();
        try {
            ticketService.save(ticketConfig);
            logger.info("Ticket configuration saved successfully.");
            response.put("message", "Ticket configuration saved successfully.");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.severe("Error saving ticket configuration: " + e.getMessage());
            response.put("message", "Error saving configuration.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/start")
    public ResponseEntity<String> startSystem() {
        ticketService.loadConfigAndStart();
        return ResponseEntity.ok("Ticketing System started successfully.");
    }

}


