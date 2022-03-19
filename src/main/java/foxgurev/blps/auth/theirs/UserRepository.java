package foxgurev.blps.auth.theirs;

import lombok.var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    default Optional<Role> findRoleByEmail(final String email) {
        final var user = findByEmail(email);
        return user.map(User::getRole);
    }
}
