package se.ifmo.blos.lab2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.blos.lab2.domains.CreditStatus;

import javax.validation.constraints.DecimalMin;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDto implements Dto {

    @JsonProperty(access = READ_ONLY)
    private UUID id;

    @DecimalMin("1")
    private Double price;

    @JsonProperty(access = READ_ONLY)
    private Long applicantId;

    @JsonProperty(access = READ_ONLY)
    private String applicantEmail;

    @JsonProperty(access = READ_ONLY)
    private Long managerId;

    @JsonProperty(access = READ_ONLY)
    private String managerEmail;

    private CreditStatus status;
}
