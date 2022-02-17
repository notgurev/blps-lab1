package foxgurev.blps.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreationRequest {
    List<Long> products; // product ids
    String promocode; // todo validate
    String name;
    String surname;
    String phoneNumber;
    String email;
    String city;
}
