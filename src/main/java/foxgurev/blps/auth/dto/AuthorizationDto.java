package foxgurev.blps.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import foxgurev.blps.auth.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationDto implements Serializable {
    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private Long id;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String username;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String email;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String phoneNumber;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_NULL)
    private String password;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private String token;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSXXX", timezone = "UTC")
    private Instant expiresAt;

    @JsonProperty(access = READ_ONLY)
    @JsonInclude(NON_NULL)
    private Role role;
}
