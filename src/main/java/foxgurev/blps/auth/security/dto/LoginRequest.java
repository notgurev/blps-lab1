package foxgurev.blps.auth.security.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userLogin;
    private String password;
}
