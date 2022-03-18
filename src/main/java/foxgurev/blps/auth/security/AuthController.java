package foxgurev.blps.auth.security;

import foxgurev.blps.auth.security.dto.JwtResponse;
import foxgurev.blps.auth.security.dto.LoginRequest;
import foxgurev.blps.auth.security.dto.MessageResponse;
import foxgurev.blps.auth.security.dto.RegisterRequest;
import foxgurev.blps.auth.security.jwt.JwtTokenUtil;
import foxgurev.blps.auth.user.User;
import foxgurev.blps.auth.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserRepository userRepo, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserLogin(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //todo: roles
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUserLogin(),
                userDetails.getEmail()));
    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid RegisterRequest request) {
        if (userRepo.existsByUserLogin(request.getUserLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(
                request.getUserLogin(),
                request.getEmail(),
                encoder.encode(request.getPassword())
        );

        //todo: назначить роль ROLE_USER
        userRepo.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserLogin(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUserLogin(),
                user.getEmail()));
    }
}
