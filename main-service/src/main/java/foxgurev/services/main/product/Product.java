package foxgurev.services.main.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private Integer inStock;

    @Column
    private Integer watermark;

    @Column
    private Boolean markedForResupply;

    public Product(String name, Integer price, Integer inStock, Integer watermark) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.watermark = watermark;
        this.markedForResupply = false;
    }

    public int changeAmountInStock(int delta) {
        if (inStock == 0 && delta < 0) throw new RuntimeException("No amount of product left in stock");
        inStock += delta;
        return inStock;
    }
}
