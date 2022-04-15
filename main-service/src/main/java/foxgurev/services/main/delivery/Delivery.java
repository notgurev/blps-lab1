package foxgurev.services.main.delivery;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private long orderId;

    @Column
    private LocalDateTime date;

    public Delivery(long order_id, LocalDateTime date) {
        this.orderId = order_id;
        this.date = date;
    }
}
