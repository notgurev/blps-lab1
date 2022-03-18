package foxgurev.blps.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static foxgurev.blps.util.MapBuilder.map;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/create")
    public Map<String, Object> createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        return map("message", "order created").put("id", orderService.createOrder(orderCreationRequest)).build();
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
