package foxgurev.blps.auth;

import foxgurev.blps.auth.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class UserAuthorityGranter implements AuthorityGranter {
    private final UserRepository userRepository;

    @Override
    public Set<String> grant(Principal principal) {
        String email = principal.getName();
        Optional<Role> optionalRole = userRepository.findRoleByEmail(email);
        Role role = optionalRole.orElseThrow(() -> new RuntimeException("Failed to find role of user by email"));
        log.info("Granting role " + role.name() + " to " + email);
        return Collections.singleton(role.name());
    }
}
