package foxgurev.blps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    final KafkaTemplate<String, String> template;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendMessage(String message) {
        log.info(String.format("#### -> Producing message -> %s", message));
        template.send("new-mailing-list-entries", message);
    }

    @Scheduled(fixedRate = 3000)
    public void spam() {
        sendMessage("spam!!!");
    }
}
