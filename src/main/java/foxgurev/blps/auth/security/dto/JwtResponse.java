package foxgurev.blps.auth.security.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private Long id;
    private String userLogin;
    private String email;

    public JwtResponse(String token, Long id, String userLogin, String email) {
        this.token = token;
        this.id = id;
        this.userLogin = userLogin;
        this.email = email;
    }
}
