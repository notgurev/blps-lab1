package se.ifmo.blos.lab2.repositories;

import lombok.var;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab2.domains.Role;
import se.ifmo.blos.lab2.domains.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PersistableRepository<User, Long> {
    boolean existsByEmail(final String email);

    Optional<User> findByEmail(final String email);

    @Query("SELECT u FROM cars c JOIN c.owner u WHERE c.id = ?1")
    Optional<User> findByCarId(final UUID carId);

    default Optional<Role> findRoleByEmail(final String email) {
        final var user = findByEmail(email);
        return user.map(User::getRole);
    }
}
