package foxgurev.common;

import lombok.Data;

@Data
public class KafkaMessage {
    String message;
    int value;
}
