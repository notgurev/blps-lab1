package foxgurev.blps.auth.theirs;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    @SneakyThrows
    public AuthorizationDto authorize(final AuthorizationDto authorizationDto) {
        var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authorizationDto.getUsername(), authorizationDto.getPassword())
                );
        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Bad credentials");
        }
        String token = jwtUtil.generateJwtToken(authentication);
        var user = userRepository.findByEmail(authentication.getName()).get();
        authorizationDto.setId(user.getId());
        authorizationDto.setToken(token);
        authorizationDto.setRole(user.getRole());
        authorizationDto.setExpiresAt(jwtUtil.getExpirationDate(token).toInstant());
        return authorizationDto;
    }
}
