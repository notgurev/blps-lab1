package foxgurev.services.main.auth;

import foxgurev.services.main.auth.user.Role;
import foxgurev.services.main.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    default Optional<Role> findRoleByEmail(String email) { // todo not in interface -> move to service
        Optional<User> user = findUserByEmail(email);
        return user.map(User::getRole);
    }
}