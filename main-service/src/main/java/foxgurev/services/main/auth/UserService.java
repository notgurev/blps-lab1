package foxgurev.services.main.auth;

import foxgurev.services.main.auth.dto.requests.LoginRequest;
import foxgurev.services.main.auth.dto.requests.UserDto;
import foxgurev.services.main.auth.dto.responses.LoginDto;
import foxgurev.services.main.auth.dto.responses.LoginResponse;
import foxgurev.services.main.auth.user.Role;
import foxgurev.services.main.auth.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public boolean checkEmailTaken(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public boolean saveUser(UserDto u) {
        if (checkEmailTaken(u.getEmail())) {
            return false;
        }

        User user = new User(); // todo convert properly
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setPhoneNumber(u.getPhoneNumber());
        user.setUsername(u.getUsername());
        user.setRole(Role.ROLE_USER);

        user.setPassword(passwordEncoder.encode(user.getPassword())); // todo get from dto

        userRepository.save(user);

        return true;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Incorrect user data"); // todo exception class
        }

        User user = userRepository.findUserByEmail(loginRequest.getEmail()).get();

        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException("Failed to find user by email"); // todo exception class
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password"); // todo exception class
        }

        LoginDto loginDto = new LoginDto(user.getId(), user.getEmail());
        String token = jwtUtil.generateToken(loginRequest.getEmail());
        return new LoginResponse(token, loginDto);
    }

    public List<String> getAllEmails() {
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }
}
