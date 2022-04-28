package foxgurev.services.greeting;

import lombok.Data;

@Data
public class KafkaMessage {
    String message;
    int value;
}
