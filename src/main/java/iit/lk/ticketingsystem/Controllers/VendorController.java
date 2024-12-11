package iit.lk.ticketingsystem.Controllers;

import iit.lk.ticketingsystem.Models.Vendor;
import iit.lk.ticketingsystem.Services.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/test")
    public String test() {
        return vendorService.test();
    }

    @PostMapping("/save-vendor")
    public ResponseEntity<String> saveVendor(@RequestBody Vendor vendor) {
        System.out.println("Received vendor: " + vendor);
        try {
            vendorService.saveVendor(vendor);
            return ResponseEntity.ok("Vendor saved successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            return ResponseEntity.status(500).body("Error saving vendor: " + e.getMessage());
        }
    }



    @GetMapping("/all")
    public ResponseEntity<List<Vendor>> getAllVendors() throws IOException {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) throws IOException {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully.");
    }
}
