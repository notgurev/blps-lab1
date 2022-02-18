package foxgurev.blps.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, CustomizedDelivery {
    LocalDateTime getLastDate();
}
