package foxgurev.blps.order;

import foxgurev.blps.delivery.Delivery;
import foxgurev.blps.delivery.DeliveryService;
import foxgurev.blps.exceptions.VisibleException;
import foxgurev.blps.product.Product;
import foxgurev.blps.product.ProductRepository;
import foxgurev.blps.product.ProductService;
import foxgurev.blps.promocode.InactivePromocodeException;
import foxgurev.blps.promocode.Promocode;
import foxgurev.blps.promocode.PromocodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final PromocodeService promocodeService;
    private final ProductRepository productRepository;
    private final DeliveryService deliveryService;
    private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, PromocodeService promocodeService, ProductRepository productRepository, DeliveryService deliveryService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.promocodeService = promocodeService;
        this.productRepository = productRepository;
        this.deliveryService = deliveryService;
        this.productService = productService;
    }

    public Long createOrder(OrderCreationRequest ocr) {
        Optional<Promocode> opc = promocodeService.getPromocode(ocr.promocode);
        Promocode promocode = opc.orElseThrow(InactivePromocodeException::new);

        List<Product> products = productRepository.findAllById(ocr.getProducts());

        products.forEach(p -> p.changeAmountInStock(-1));

        Integer sum = products.stream().map(Product::getPrice).reduce(Integer::sum).orElseThrow(() -> {
            throw new RuntimeException("Failed to calculate sum of order");
        }) * (100 - promocode.getDiscount()) / 100;

        Order o = new Order(OrderStatus.CREATED, products, promocode, sum, ocr.name,
                ocr.surname, ocr.phoneNumber, ocr.email, ocr.city);
        Order saved = orderRepository.save(o);
        return saved.getId();
    }

    public void changeStatus(long id, OrderStatus newStatus) {
        Order order = findOrder(id);
        checkStatusFlow(order.getStatus(), newStatus);
        order.setStatus(newStatus);
    }

    private Order findOrder(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new VisibleException("Order (id = " + id + ") doesn't exist")
        );
    }

    private void checkStatusFlow(OrderStatus current, OrderStatus next) {
        if (current == OrderStatus.CANCELLED) {
            throw new VisibleException("Changing status of cancelled order is not allowed");
        }

        if (next == OrderStatus.CANCELLED) {
            return;
        }

        switch (next.ordinal() - current.ordinal()) {
            case 0:
                throw new VisibleException("This status is already assigned");
            case 1:
                return;
            default:
                throw new VisibleException("Illegal change of status (" + current.name() + " -> " + next.name() + ")");
        }
    }

    public void cancelOrder(long id) {
        Order order = findOrder(id);
        switch (order.getStatus()) {

            case CANCELLED:
                throw new VisibleException("This order is already cancelled");
            case DELIVERED:
                throw new VisibleException("Cannot cancel a delivered order");

            case SHIPPING:
                throw new VisibleException("Cannot cancel an order in shipping");

            case PACKED:
                deliveryService.cancelDelivery(id);
            case CREATED:
                productService.returnToStock(order.getItems());
        }
        order.setStatus(OrderStatus.CANCELLED);
    }

    public void packOrderAndOrganizeDelivery(long orderId) {
        changeStatus(orderId, OrderStatus.PACKED);
        LocalDateTime freeDate = deliveryService.selectDeliveryDate();
        deliveryService.addDelivery(new Delivery(orderId, freeDate));
    }
}
