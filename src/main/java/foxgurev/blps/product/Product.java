package foxgurev.blps.product;

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

    public Product(String name, Integer price, Integer inStock) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }
}
