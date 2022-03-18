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
    private final LocalTime startTime = LocalTime.of(9, 0);
    private final LocalTime endTime = LocalTime.of(18, 0);
    private final OrderService orderService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, OrderService orderService) {
        this.deliveryRepository = deliveryRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void addDelivery(long order_id) {
        orderService.changeStatus(order_id, OrderStatus.PACKED);

        Optional<Delivery> delivery = deliveryRepository.findByOrderByDate();
        LocalDateTime freeDate;
        if (!delivery.isPresent()) {
            freeDate = LocalDateTime.now();
        } else {
            freeDate = delivery.get().getDate();
            if (LocalTime.of(freeDate.getHour() + 1, freeDate.getMinute()).isAfter(endTime)) {
                freeDate = freeDate.plusDays(1);
                freeDate = LocalDateTime.of(LocalDate.of(freeDate.getYear(), freeDate.getMonth(), freeDate.getDayOfMonth()),
                        startTime);
            } else {
                freeDate = freeDate.plusHours(1);
            }
        }
        deliveryRepository.save(new Delivery(order_id, freeDate));
    }
}
