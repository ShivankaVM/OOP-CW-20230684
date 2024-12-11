package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Services.CLIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cli")
public class CLIController {

    private final CLIService cliService;
    private final WebSocketController webSocketController; // Inject WebSocketController

    @Autowired
    public CLIController(CLIService cliService, WebSocketController webSocketController) {
        this.cliService = cliService;
        this.webSocketController = webSocketController;
    }

    @PostMapping("/start")
    public String startCLI() {
        String response = cliService.startSystem();

        // Sample values for demonstration
        int availableTickets = 100; // Fetch this from the system if applicable
        int totalTickets = 200;     // Fetch this from the system if applicable

        webSocketController.sendTicketUpdate(availableTickets, totalTickets);

        return response;
    }

    @PostMapping("/stop")
    public String stopCLI() {
        return cliService.stopSystem();
    }
}
