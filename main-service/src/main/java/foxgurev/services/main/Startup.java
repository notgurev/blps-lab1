package foxgurev.services.main;

import foxgurev.services.main.auth.UserRepository;
import foxgurev.services.main.auth.user.Role;
import foxgurev.services.main.auth.user.User;
import foxgurev.services.main.product.Product;
import foxgurev.services.main.product.ProductRepository;
import foxgurev.services.main.promocode.Promocode;
import foxgurev.services.main.promocode.PromocodeRepository;
import foxgurev.services.main.promocode.PromocodeStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@Slf4j
public class Startup {
    private final ProductRepository productRepository;
    private final PromocodeRepository promocodeRepository;
    private final UserRepository userRepository;

    @Autowired
    public Startup(ProductRepository productRepository, PromocodeRepository promocodeRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.promocodeRepository = promocodeRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        if (productRepository.count() > 0) {
            log.info("Already initialized, skipping adding initial entities");
            return;
        }

        log.info("Adding initial entities...");

        productRepository.saveAll(Arrays.asList(
                new Product("Набор для шитья", 100, 1000, 999),
                new Product("Пластилин", 250, 2000, 200),
                new Product("Полимерная глина", 150, 500, 499)
        ));

        promocodeRepository.saveAll(Arrays.asList(
                new Promocode("GORBUNOV", 50, PromocodeStatus.ACTIVE),
                new Promocode("USKOV", 50, PromocodeStatus.INACTIVE)
        ));

        userRepository.save(new User("user@sd.com", "username",
                "$2b$12$CekwqWUxTHJKKCa8qEAOo.8pyOhGMMjdKDoBceMqAAp4/2TEAdr2.",
                "89023457654", Role.ROLE_USER));

        userRepository.save(new User("admin@sd.com", "admin",
                "$2b$12$CekwqWUxTHJKKCa8qEAOo.8pyOhGMMjdKDoBceMqAAp4/2TEAdr2.",
                "88005555555",
                Role.ROLE_ADMIN));

        log.info("Successfully added initial entities");
    }
}
