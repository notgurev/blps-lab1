package foxgurev.services.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlpsLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(foxgurev.greeting.BlpsLabApplication.class, args);
    }
}
