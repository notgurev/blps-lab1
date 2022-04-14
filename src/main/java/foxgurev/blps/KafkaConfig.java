package foxgurev.blps;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1").partitions(2).replicas(1).build();
    }

    // consumer
    // это в отдельный сервис надо (или сервер)
    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(String in) {
        System.out.println(in);
    }
}
