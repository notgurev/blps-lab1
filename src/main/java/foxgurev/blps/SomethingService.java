package foxgurev.blps;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SomethingService {
    /*
    тут типа producer :)
     */
    KafkaTemplate<String, String> template;
}
