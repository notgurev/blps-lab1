package foxgurev.blps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // todo если не работает, то поставить над классом @Config AppConfig
public class BlpsLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlpsLabApplication.class, args);
    }
}
