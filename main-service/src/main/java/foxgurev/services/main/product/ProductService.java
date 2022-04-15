package foxgurev.services.main.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findTop10ByOrderById();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public void returnToStock(List<Product> items) {
        items.forEach(product -> product.changeAmountInStock(+1));
        productRepository.saveAll(items);
    }
}
