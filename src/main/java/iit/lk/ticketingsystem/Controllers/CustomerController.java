package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.Customer;
import iit.lk.ticketingsystem.Services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String test() {
        return customerService.test();
    }

    @PostMapping("/customers")
    public ResponseEntity<String> saveCustomers(@RequestBody List<Customer> customers) {
        customerService.saveAll(customers);
        return ResponseEntity.ok("Customers saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() throws IOException {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) throws IOException {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully.");
    }
}

