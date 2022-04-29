package foxgurev.services.main.order;

import foxgurev.common.ProductSupply;
import foxgurev.services.main.delivery.Delivery;
import foxgurev.services.main.delivery.DeliveryService;
import foxgurev.services.main.exceptions.VisibleException;
import foxgurev.services.main.product.Product;
import foxgurev.services.main.product.ProductRepository;
import foxgurev.services.main.product.ProductService;
import foxgurev.services.main.promocode.InactivePromocodeException;
import foxgurev.services.main.promocode.Promocode;
import foxgurev.services.main.promocode.PromocodeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PromocodeService promocodeService;
    private final ProductRepository productRepository;
    private final DeliveryService deliveryService;
    private final ProductService productService;

    private final KafkaTemplate<String, ProductSupply> productSupplyQueue;

    @SneakyThrows
    public Long createOrder(OrderCreationRequest ocr) {
        Optional<Promocode> opc = promocodeService.getPromocode(ocr.promocode);
        Promocode promocode = opc.orElseThrow(InactivePromocodeException::new);

        List<Product> products = productRepository.findAllById(ocr.getProducts());

        products.forEach(p -> {
            int inStock = p.changeAmountInStock(-1);
            if (inStock <= p.getWatermark() && !p.getMarkedForResupply()) {
                p.setMarkedForResupply(true);
                log.info("Marked product with id = {} for resupply", p.getId());
                productSupplyQueue.send("resupply", new ProductSupply(p.getId(), 1));
            }
        });

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
