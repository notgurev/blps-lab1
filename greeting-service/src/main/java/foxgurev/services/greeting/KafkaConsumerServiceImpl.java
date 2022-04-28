package foxgurev.services.greeting;

import foxgurev.common.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerServiceImpl {
    @KafkaListener(topics = "greetings", groupId = "greetings")
    public void listenToMessage(KafkaMessage msg) {
        log.error(msg.toString());
    }
}