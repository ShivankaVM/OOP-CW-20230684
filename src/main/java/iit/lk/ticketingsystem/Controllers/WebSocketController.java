package iit.lk.ticketingsystem.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendTicketUpdate(int availableTickets, int totalTickets) {
        String message = "Available tickets: " + availableTickets + "/" + totalTickets;
        logger.info(message); // Log to terminal
        messagingTemplate.convertAndSend("/topic/ticketPool", message); // Send to frontend
    }

    // New method to send CLI logs
    public void sendCliLog(String log) {
        logger.info("CLI Log: " + log);
        messagingTemplate.convertAndSend("/topic/cli-logs", log);
    }
}