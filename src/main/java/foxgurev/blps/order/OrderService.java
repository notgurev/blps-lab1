package foxgurev.blps.order;

import foxgurev.blps.product.Product;
import foxgurev.blps.product.ProductRepository;
import foxgurev.blps.promocode.InactivePromocodeException;
import foxgurev.blps.promocode.Promocode;
import foxgurev.blps.promocode.PromocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PromocodeService promocodeService;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, PromocodeService promocodeService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.promocodeService = promocodeService;
        this.productRepository = productRepository;
    }

    public void createOrder(OrderCreationRequest orderCreationRequest) {
        Optional<Promocode> opc = promocodeService.getPromocode(orderCreationRequest.promocode);
        Promocode promocode = opc.orElseThrow(InactivePromocodeException::new);

        List<Product> items = productRepository.findAllById(orderCreationRequest.getProducts());
        Integer sum = items.stream().map(Product::getPrice).reduce(Integer::sum).orElseThrow(() -> {
            throw new RuntimeException("Failed to calculate sum of order");
        });

        orderRepository.save(new Order(OrderStatus.CREATED, items, promocode, sum,
                orderCreationRequest.name, orderCreationRequest.surname, orderCreationRequest.phoneNumber,
                orderCreationRequest.email, orderCreationRequest.city));
    }

    public void changeStatus(long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id).orElse(new Order()); //todo error message
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
