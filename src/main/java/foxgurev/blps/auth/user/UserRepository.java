package foxgurev.blps.auth.user;

import lombok.var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default Optional<Role> findRoleByEmail(final String email) {
        final var user = findByEmail(email);
        return user.map(User::getRole);
    }
}
