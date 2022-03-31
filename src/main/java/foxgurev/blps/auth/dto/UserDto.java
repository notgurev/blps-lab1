package foxgurev.blps.auth.dto;

import foxgurev.blps.auth.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private String email;
    private String phoneNumber;
    private Role role;
}
