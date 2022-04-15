package foxgurev.blps;

import foxgurev.blps.auth.UserRepository;
import foxgurev.blps.auth.user.Role;
import foxgurev.blps.auth.user.User;
import foxgurev.blps.product.Product;
import foxgurev.blps.product.ProductRepository;
import foxgurev.blps.promocode.Promocode;
import foxgurev.blps.promocode.PromocodeRepository;
import foxgurev.blps.promocode.PromocodeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class Startup {
    private final ProductRepository productRepository;
    private final PromocodeRepository promocodeRepository;
    private final UserRepository userRepository;

//    @Value("${spring.profiles.active}")
//    private String activeProfile = "placeholder"; // todo

    private final Logger logger = LoggerFactory.getLogger(Startup.class);

    @Autowired
    public Startup(ProductRepository productRepository, PromocodeRepository promocodeRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.promocodeRepository = promocodeRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
//        logger.info("Active profile: " + activeProfile);

        if (productRepository.count() > 0) {
            logger.info("Already initialized, skipping adding initial entities");
            return;
        }

        logger.info("Adding initial entities...");

        productRepository.saveAll(Arrays.asList(
                new Product("Набор для шитья", 100, 1000),
                new Product("Пластилин", 250, 2000),
                new Product("Полимерная глина", 150, 500)
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

        logger.info("Successfully added initial entities");
    }
}
