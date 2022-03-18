package se.ifmo.blos.lab2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.blos.lab2.domains.Role;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private String email;
    private String phoneNumber;
    private Role role;
}
