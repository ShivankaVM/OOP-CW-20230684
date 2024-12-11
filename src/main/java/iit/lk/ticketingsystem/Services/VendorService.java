package iit.lk.ticketingsystem.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import iit.lk.ticketingsystem.Models.Vendor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {

    private final File vendorFile = new File("vendor.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String test() {
        return "Vendor Service is working!";
    }

    public void saveVendor(Vendor vendor) throws IOException {
        if (vendor == null) {
            throw new IllegalArgumentException("Vendor cannot be null");
        }

        if (!vendorFile.exists()) {
            vendorFile.createNewFile();
        }

        List<Vendor> vendors = getAllVendors();
        vendors.add(vendor);
        objectMapper.writeValue(vendorFile, vendors);
    }

    public List<Vendor> getAllVendors() throws IOException {
        if (!vendorFile.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(vendorFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Vendor.class));
    }

    public void deleteVendor(Long id) throws IOException {
        List<Vendor> vendors = getAllVendors();
        vendors.removeIf(v -> v.getId().equals(id));
        objectMapper.writeValue(vendorFile, vendors);
    }
}
