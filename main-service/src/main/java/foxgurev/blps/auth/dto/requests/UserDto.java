package foxgurev.blps.auth.dto.requests;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String username;
}
