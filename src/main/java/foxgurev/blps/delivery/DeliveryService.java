package foxgurev.blps.delivery;

import foxgurev.blps.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    private final LocalTime startTime = LocalTime.of(9, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public LocalDateTime selectDeliveryDate() {
        Optional<Delivery> delivery = deliveryRepository.findByOrderByDate();
        if (!delivery.isPresent()) {
            return LocalDateTime.now();
        }

        LocalDateTime date = delivery.get().getDate();
        if (LocalTime.of(date.getHour() + 1, date.getMinute()).isAfter(endTime)) {
            date = date.plusDays(1);
            return LocalDateTime.of(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()), startTime);
        }

        return date.plusHours(1);
    }

    public void cancelDelivery(long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId).orElseThrow(
                () -> new BadRequestException("Failed to find delivery for order #" + orderId)
        );
        deliveryRepository.delete(delivery);
    }

    public void addDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }
}
