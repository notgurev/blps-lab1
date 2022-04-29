package foxgurev.services.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BlpsLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlpsLabApplication.class, args);
    }
}
