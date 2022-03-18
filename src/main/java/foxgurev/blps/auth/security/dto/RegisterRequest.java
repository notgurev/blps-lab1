package foxgurev.blps.auth.security.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userLogin;
    private String email;
    private String password;
}
