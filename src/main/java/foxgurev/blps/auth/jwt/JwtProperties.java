package foxgurev.blps.auth.jwt;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "token")
@ConstructorBinding
@Data
@RequiredArgsConstructor
public class JwtProperties {
    private final String authoritiesClaim;
    private final String jwtSecret;
    private final int expirationHours;
}
