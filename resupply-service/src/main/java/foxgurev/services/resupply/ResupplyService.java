package foxgurev.services.resupply;

import foxgurev.common.ProductSupply;
import foxgurev.common.SuppliesArrivalNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResupplyService {
    private List<ProductSupply> queue = new ArrayList<>();
    private final KafkaTemplate<String, SuppliesArrivalNotification> suppliesArrivalNotifications;

    @Scheduled(fixedRate = 10_000) // для демонстрации
    // @Scheduled(cron = "0 12 * * *") // каждый день в 12:00
    public void resupply() {
        log.info("SCHEDULED TASK: resupplying...");
        if (queue.isEmpty()) {
            log.info("Queue is empty, nothing to resupply");
            return;
        }
        List<ProductSupply> q = queue;
        clearQueue();
        suppliesArrivalNotifications.send("supplies-arrival-notifications", new SuppliesArrivalNotification(q));
    }

    public void add(ProductSupply ps) {
        queue.add(ps);
    }

    private void clearQueue() {
        queue = new ArrayList<>();
    }
}
