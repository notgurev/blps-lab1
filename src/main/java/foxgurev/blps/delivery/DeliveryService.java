package foxgurev.blps.delivery;

import foxgurev.blps.order.OrderService;
import foxgurev.blps.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;

    private final LocalTime startTime = LocalTime.of(9, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, OrderService orderService) {
        this.deliveryRepository = deliveryRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void packOrderAndPrepareDelivery(long orderId) { // todo rename
        orderService.changeStatus(orderId, OrderStatus.PACKED);
        LocalDateTime freeDate = selectDeliveryDate();
        deliveryRepository.save(new Delivery(orderId, freeDate));
    }

    private LocalDateTime selectDeliveryDate() { // todo throw something to test packOrderAndPrepareDelivery
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
}
