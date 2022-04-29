package foxgurev.services.main;

import foxgurev.common.SuppliesArrivalNotification;
import foxgurev.services.main.product.ProductRepository;
import foxgurev.services.main.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SuppliesArrivalNotificationListener {
    private final ProductService productService;

    @KafkaListener(topics = "supplies-arrival-notifications")
    public void saveSupplies(SuppliesArrivalNotification san) {
        san.products.forEach(productSupply -> {
            productService.getProduct(productSupply.getProductID()).ifPresent(product -> {
                log.info("Saving to stock: id = {}, amount = {}", product.getId(), productSupply.getAmount());
                product.changeAmountInStock((int) productSupply.getAmount());
            });
        });
    }
}
