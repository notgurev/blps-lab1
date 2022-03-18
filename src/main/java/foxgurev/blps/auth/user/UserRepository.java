package foxgurev.blps.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserLogin(String userLogin);
    Boolean existsByUserLogin(String userLogin);
    Boolean existsByEmail(String email);
}
