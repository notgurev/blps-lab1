package foxgurev.blps.auth;

import foxgurev.blps.auth.dto.AuthorizationDto;
import foxgurev.blps.auth.exceptions.UnauthorizedException;
import foxgurev.blps.auth.jwt.JwtUtil;
import foxgurev.blps.auth.user.Role;
import foxgurev.blps.auth.user.User;
import foxgurev.blps.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationService {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Transactional
    @SneakyThrows
    public AuthorizationDto signIn(final AuthorizationDto authorizationDto) {
        logger.info("Start authorization");
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authorizationDto.getUsername(), authorizationDto.getPassword())
                );
        if (!authentication.isAuthenticated()) {
            logger.warn("Bad credentials");
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

    public AuthorizationDto signUp(final AuthorizationDto authorizationDto) {
        logger.info("Start registration");
        User user = new User(
                authorizationDto.getEmail(),
                authorizationDto.getUsername(),
                encoder.encode(authorizationDto.getPassword()),
                authorizationDto.getPhoneNumber(),
                Role.ROLE_USER
        );
        userRepository.save(user);
        logger.info("New user was saved");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authorizationDto.getUsername(), authorizationDto.getPassword()));
        logger.info("Authentication Manager was passed");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(authentication);
        logger.info("Token: " + token);
        var userSaved = userRepository.findByEmail(authentication.getName()).get();
        authorizationDto.setId(userSaved.getId());
        authorizationDto.setToken(token);
        authorizationDto.setRole(userSaved.getRole());
        authorizationDto.setExpiresAt(jwtUtil.getExpirationDate(token).toInstant());
        return authorizationDto;
    }
}
