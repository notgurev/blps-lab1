package foxgurev.blps.auth.theirs;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRepositoryAuthorityGranter implements AuthorityGranter {
    private final UserRepository userRepository;

    @Override
    public Set<String> grant(Principal principal) {
        final var role = userRepository.findRoleByEmail(principal.getName());
        return role.map(value -> Set.of(value.toString())).orElse(null);
    }
}
