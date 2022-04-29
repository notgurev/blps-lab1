package foxgurev.services.resupply;

import foxgurev.common.ProductSupply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResupplyRequestsConsumer {
    private final ResupplyService resupplyService;

    @KafkaListener(topics = "resupply", groupId = "resupply-group")
    public void listenToMessage(ProductSupply ps) {
        log.info("Accepted product resupply request: id = {}, amount = {}", ps.getProductID(), ps.getAmount());
        resupplyService.add(ps);
    }
}