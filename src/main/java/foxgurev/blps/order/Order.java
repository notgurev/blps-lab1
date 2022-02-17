package foxgurev.blps.order;

import foxgurev.blps.product.Product;
import foxgurev.blps.promocode.Promocode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private OrderStatus status;

    @ManyToMany
    private List<Product> items;

    @ManyToOne
    private Promocode promocode;

    @Column
    private Integer totalPrice;

    public Order(OrderStatus status, List<Product> items, Promocode promocode, Integer totalPrice) {
        this.status = status;
        this.items = items;
        this.promocode = promocode;
        this.totalPrice = totalPrice;
    }
}
