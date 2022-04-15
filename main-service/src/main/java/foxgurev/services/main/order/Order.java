package foxgurev.services.main.order;

import foxgurev.services.main.product.Product;
import foxgurev.services.main.promocode.Promocode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orderr")
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

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private String city;

    public Order(OrderStatus status, List<Product> items, Promocode promocode, Integer totalPrice, String name,
                 String surname, String phoneNumber, String email, String city) {
        this.status = status;
        this.items = items;
        this.promocode = promocode;
        this.totalPrice = totalPrice;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
    }
}
