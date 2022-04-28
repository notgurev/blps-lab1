package foxgurev.services.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaMessage {
    String message;
    int value;
}
