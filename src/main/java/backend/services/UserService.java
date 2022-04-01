package backend.services;

import backend.dto.requests.LoginRequest;
import backend.dto.requests.UserDto;
import backend.dto.responses.LoginDto;
import backend.dto.responses.LoginResponse;
import backend.entities.Role;
import backend.entities.User;
import backend.repositories.UserRepository;
import backend.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public boolean checkEmailTaken(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public boolean saveUser(UserDto u) {
        if (checkEmailTaken(u.getEmail())) {
            return false;
        }

        User user = new User(); // todo convert properly
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setAge(u.getAge());
        user.setRole(Role.USER);

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

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password"); // todo exception class
        }

        LoginDto loginDto = new LoginDto(user.getId(), user.getEmail());
        String token = jwtUtil.generateToken(loginRequest.getEmail());
        return new LoginResponse(token, loginDto);
    }
}
