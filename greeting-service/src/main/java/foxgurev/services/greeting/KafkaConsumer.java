package foxgurev.services.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @KafkaListener(id = "greeting-service", topics = "new-mailing-list-entries")
    public void listen(String in) {
        log.info(in);
    }
}
