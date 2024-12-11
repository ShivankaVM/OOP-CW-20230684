package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.Customer;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CustomerService {

    private static final String FILE_PATH = "customers.json";
    private final ObjectMapper objectMapper;

    public CustomerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String test() {
        return "Service is working!";
    }

    public void saveAll(List<Customer> customers) {
        try {
            // Write the list of customers to a JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            // Read the list of customers from the JSON file
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        }
        return List.of(); // Return an empty list if no data exists
    }

    public void deleteCustomer(Long id) throws IOException {
        List<Customer> customers = getAllCustomers();
        customers.removeIf(customer -> customer.getId().equals(id));

        // Write the updated list back to the JSON file
        saveAll(customers);
    }
}
