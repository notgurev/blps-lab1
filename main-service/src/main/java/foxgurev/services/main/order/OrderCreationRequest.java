package foxgurev.services.main.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreationRequest {
    List<Long> products; // product ids
    String promocode;
    String name;
    String surname;
    String phoneNumber;
    String email;
    String city;
}
