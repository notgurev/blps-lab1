package foxgurev.blps.auth.security.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
