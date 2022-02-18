package foxgurev.blps.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final LocalTime startTime = LocalTime.of(9, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public void addDelivery(long order_id) {
        LocalDateTime freeDate = deliveryRepository.getLastDate();
        if (LocalTime.of(freeDate.getHour() + 1, freeDate.getMinute()).isAfter(endTime)) {
            freeDate = freeDate.plusDays(1);
            freeDate = LocalDateTime.of(LocalDate.of(freeDate.getYear(), freeDate.getMonth(), freeDate.getDayOfMonth()),
                    startTime);
        } else {
            freeDate = freeDate.plusHours(1);
        }
        deliveryRepository.save(new Delivery(order_id, freeDate));
    }
}
