package foxgurev.services.main;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Configuration
public class GreetingQueue {
    private final KafkaTemplate<String, String> template;
    private final static String topic = "new-mailing-list-entries";

//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name(topic).partitions(2).replicas(1).build();
//    }

    @Autowired
    public GreetingQueue(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendMessage(String message) {
        template.send(topic, message);
    }
}
