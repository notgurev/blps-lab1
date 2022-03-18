package foxgurev.blps.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static foxgurev.blps.util.MapBuilder.map;

/*
@PreAuthorize("isAuthenticated() and authentication.principal.id == #userId")
 */

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/create")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    public Map<String, Object> createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        return map("message", "order created").put("id", orderService.createOrder(orderCreationRequest)).build();
    }

    @PostMapping("/order/pack/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    public void packOrder(@PathVariable long id) {
        orderService.packOrderAndOrganizeDelivery(id);
    }

    @PostMapping("/order/ship/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    public void shipOrder(@PathVariable long id) {
        orderService.changeStatus(id, OrderStatus.SHIPPING);
    }

    @PostMapping("/order/delivered/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    public void deliveredOrder(@PathVariable long id) {
        orderService.changeStatus(id, OrderStatus.DELIVERED);
    }

    @PostMapping("/order/cancel/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
    public void cancelOrder(@PathVariable long id) {
        orderService.cancelOrder(id);
    }
}
