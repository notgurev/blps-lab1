package se.ifmo.blos.lab2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab2.domains.Car;

import java.util.UUID;

@Repository("carRepository")
public interface CarRepository extends PersistableRepository<Car, UUID> {

    boolean existsByVin(final String vin);

    @Query("SELECT c FROM cars c WHERE c.isSold = false")
    Page<Car> findAllPublic(final Specification<Car> specification, final Pageable pageable);

    @Query(
            "SELECT c FROM cars c "
                    + "WHERE c.isSold = false OR "
                    + "(c.isSold = true AND c.owner.id = ?1)")
    Page<Car> findAllPublicOrPersonal(
            final Long ownerId, final Specification<Car> specification, final Pageable pageable);
}
