package foxgurev.blps;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(id = "KANYE", topics = "new-mailing-list-entries")
    public void listen(String in) {
        System.out.println(in);
    }
}
