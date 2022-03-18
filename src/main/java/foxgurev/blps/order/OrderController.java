package foxgurev.blps.order;

import foxgurev.blps.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @Autowired
    public OrderController(OrderService orderService, DeliveryService deliveryService) {
        this.orderService = orderService;
        this.deliveryService = deliveryService;
    }

    @PostMapping("/order/create")
    public Long createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        return orderService.createOrder(orderCreationRequest);
    }

    @PostMapping("/order/pack/{id}")
    public void packOrder(@PathVariable long id) {
        orderService.packOrderAndPrepareDelivery(id);
    }

    @PostMapping("/order/ship/{id}")
    public void shipOrder(@PathVariable long id) {
        orderService.changeStatus(id, OrderStatus.SHIPPING);
    }

    @PostMapping("/order/delivered/{id}")
    public void deliveredOrder(@PathVariable long id) {
        orderService.changeStatus(id, OrderStatus.DELIVERED);
    }

    @PostMapping("/order/cancel/{id}")
    public void cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
    }
}
