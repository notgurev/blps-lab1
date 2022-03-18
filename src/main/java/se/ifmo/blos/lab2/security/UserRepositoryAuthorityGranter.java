package se.ifmo.blos.lab2.security;

import java.security.Principal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import se.ifmo.blos.lab2.repositories.UserRepository;

@RequiredArgsConstructor
public class UserRepositoryAuthorityGranter implements AuthorityGranter {

  private final UserRepository userRepository;

  @Override
  public Set<String> grant(Principal principal) {
    final var role = userRepository.findRoleByEmail(principal.getName());
    return role.map(value -> Set.of(value.toString())).orElse(null);
  }
}
