package foxgurev.blps.order;

import foxgurev.blps.Startup;
import foxgurev.blps.exceptions.NotFoundException;
import foxgurev.blps.product.Product;
import foxgurev.blps.product.ProductRepository;
import foxgurev.blps.promocode.InactivePromocodeException;
import foxgurev.blps.promocode.Promocode;
import foxgurev.blps.promocode.PromocodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PromocodeService promocodeService;
    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, PromocodeService promocodeService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.promocodeService = promocodeService;
        this.productRepository = productRepository;
    }

    public Long createOrder(OrderCreationRequest ocr) {
        Optional<Promocode> opc = promocodeService.getPromocode(ocr.promocode);
        Promocode promocode = opc.orElseThrow(InactivePromocodeException::new);

        List<Product> items = productRepository.findAllById(ocr.getProducts());
        Integer sum = items.stream().map(Product::getPrice).reduce(Integer::sum).orElseThrow(() -> {
            throw new RuntimeException("Failed to calculate sum of order");
        }) * (100 - promocode.getDiscount()) / 100;

        Order o = new Order(OrderStatus.CREATED, items, promocode, sum,
                ocr.name, ocr.surname, ocr.phoneNumber, ocr.email, ocr.city);
        Order saved = orderRepository.save(o);
        return saved.getId();
    }

    public void changeStatus(long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("The order doesn't exist")); //todo error message
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
