package se.ifmo.blos.lab2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.blos.lab2.domains.Role;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Dto {
    @JsonProperty(access = READ_ONLY)
    private String email;

    @JsonProperty(access = READ_ONLY)
    private String phoneNumber;

    @JsonProperty(access = READ_ONLY)
    private Role role;
}
