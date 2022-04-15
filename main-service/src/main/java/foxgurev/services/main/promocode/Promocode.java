package foxgurev.services.main.promocode;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Promocode {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String code;

    @Column
    private Integer discount;

    @Column
    private PromocodeStatus status;

    public Promocode(String code, Integer discount, PromocodeStatus status) {
        this.code = code;
        this.discount = discount;
        this.status = status;
    }
}
