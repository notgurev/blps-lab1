package foxgurev.services.main;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {
    @Scheduled(fixedRate = 3000)
    public void doSomething() {
        // todo
    }
}
