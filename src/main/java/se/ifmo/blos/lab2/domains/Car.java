package se.ifmo.blos.lab2.domains;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.domain.Persistable;
import se.ifmo.blos.lab2.domains.converters.DriveUnitTypeConverter;
import se.ifmo.blos.lab2.domains.converters.FuelTypeConverter;
import se.ifmo.blos.lab2.domains.converters.GearboxTypeConverter;
import se.ifmo.blos.lab2.domains.enums.DriveUnitType;
import se.ifmo.blos.lab2.domains.enums.FuelType;
import se.ifmo.blos.lab2.domains.enums.GearboxType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true)
public class Car implements Persistable<UUID>, Serializable {

//  @Serial private static final long serialVersionUID = 6233229146413899605L;

    @Id
    @GeneratedValue
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id;

    @NaturalId(mutable = true)
    @Size(min = 17, max = 17)
    @NotBlank
    @Column(length = 17, nullable = false, unique = true)
    private String vin;

    @ManyToOne(
            fetch = LAZY,
            cascade = {DETACH, MERGE, REFRESH},
            optional = false)
    @JoinColumn(name = "brand_id")
    @ToString.Exclude
    private Brand brand;

    @Transient
    private Long brandId;

    @Size(max = 128)
    @NotBlank
    @Column(name = "model_name", length = 128, nullable = false)
    private String modelName;

    @Column(nullable = false)
    private Integer year;

    @Size(max = 128)
    @NotBlank
    @Column(length = 128, nullable = false)
    private String city;

    @DecimalMin("1")
    @Column(nullable = false)
    private Double price;

    @DecimalMin("0")
    @Column(nullable = false)
    private Double mileage;

    @Min(1)
    @Column(nullable = false)
    private Integer generation;

    @NotNull
    @Convert(converter = DriveUnitTypeConverter.class)
    @Column(name = "drive_unit_type", nullable = false)
    private DriveUnitType driveUnitType;

    @NotNull
    @Convert(converter = FuelTypeConverter.class)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @NotNull
    @Convert(converter = GearboxTypeConverter.class)
    @Column(name = "gearbox_type", nullable = false)
    private GearboxType gearboxType;

    @Size(max = 128)
    @NotBlank
    @Column(length = 128, nullable = false)
    private String color;

    @Size(min = 16, max = 8192)
    @NotBlank
    @Column(name = "additional_info", length = 8192, nullable = false)
    private String additionalInfo;

    @Column(name = "is_sold", nullable = false)
    @Builder.Default
    private boolean isSold = false;

    @Column(name = "is_hidden", nullable = false)
    @Builder.Default
    private boolean isHidden = false;

    @ManyToOne(
            fetch = LAZY,
            cascade = {DETACH, MERGE, REFRESH},
            optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    private User owner;

    @PostLoad
    public void initBrandId() {
        if (brand == null) {
            return;
        }
        brandId = brand.getId();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
