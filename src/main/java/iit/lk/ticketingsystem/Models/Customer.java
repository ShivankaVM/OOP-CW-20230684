package iit.lk.ticketingsystem.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
}
