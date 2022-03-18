package se.ifmo.blos.lab2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.blos.lab2.domains.enums.DriveUnitType;
import se.ifmo.blos.lab2.domains.enums.FuelType;
import se.ifmo.blos.lab2.domains.enums.GearboxType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto implements Dto {
    @JsonProperty(access = READ_ONLY)
    @JsonFormat(shape = STRING)
    private UUID id;

    @Size(min = 17, max = 17)
    private String vin;

    private Long brandId;

    @Size(max = 128)
    private String modelName;


    private Integer year;

    @Size(max = 128)
    private String city;

    @DecimalMin("1")
    private Double price;

    @DecimalMin("0")
    private Double mileage;

    @Min(1)
    private Integer generation;

    private DriveUnitType driveUnitType;

    private FuelType fuelType;

    private GearboxType gearboxType;

    @Size(max = 128)
    private String color;

    @Size(min = 16, max = 8192)
    private String additionalInfo;

    @JsonInclude(NON_NULL)
    private Boolean isSold;

    @JsonInclude(NON_NULL)
    private Boolean isHidden;
}
