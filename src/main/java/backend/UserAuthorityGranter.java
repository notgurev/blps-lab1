package backend;

import backend.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserAuthorityGranter implements AuthorityGranter {
    private final UserRepository userRepository;

    @Override
    public Set<String> grant(Principal principal) {
        Optional<Role> optionalRole = userRepository.findRoleByEmail(principal.getName());
        Role role = optionalRole.orElseThrow(() -> new RuntimeException("Failed to find role of user by email"));
        return Collections.singleton(String.valueOf(role));
    }
}
