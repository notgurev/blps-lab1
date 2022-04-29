package foxgurev.services.main.auth;

import foxgurev.services.main.auth.dto.requests.LoginRequest;
import foxgurev.services.main.auth.dto.requests.UserDto;
import foxgurev.services.main.auth.dto.responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user) {
        log.info("POST request to register user {}", user);
        userService.saveUser(user);
        return status(BAD_REQUEST).body("Email is taken!");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("POST request to login user {}", loginRequest);
        LoginResponse loginResponse = userService.login(loginRequest);
        return status(OK).body(loginResponse);
    }
}
