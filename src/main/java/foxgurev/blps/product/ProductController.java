package foxgurev.blps.product;

import foxgurev.blps.exceptions.VisibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @PreAuthorize("permitAll()")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("permitAll()")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id).orElseThrow(() -> new VisibleException("The product doesn't exist"));
    }
}
