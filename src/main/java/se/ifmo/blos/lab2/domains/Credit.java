package se.ifmo.blos.lab2.domains;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "credits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true)
public class Credit implements Persistable<UUID>, Serializable {

//  @Serial private static final long serialVersionUID = 6233229146413899605L;

    @Id
    @GeneratedValue
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(
            fetch = LAZY,
            cascade = {DETACH, MERGE, REFRESH},
            optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    @ToString.Exclude
    private User applicant;

    @ManyToOne(
            fetch = LAZY,
            cascade = {DETACH, MERGE, REFRESH})
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    private User manager;

    @Enumerated(STRING)
    private CreditStatus status;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
