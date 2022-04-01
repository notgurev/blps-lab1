package backend;

import backend.entity.Role;
import backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    default Role findRoleByEmail(String email) { // todo not in interface -> move to service
        Optional<User> user = findUserByEmail(email);
        return user.get().getRole(); // todo
    }
}