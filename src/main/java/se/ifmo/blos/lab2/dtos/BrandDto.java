package se.ifmo.blos.lab2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDto implements Dto {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    @JsonProperty(access = READ_ONLY)
    private String name;
}
