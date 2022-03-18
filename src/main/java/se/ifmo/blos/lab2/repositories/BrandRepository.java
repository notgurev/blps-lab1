package se.ifmo.blos.lab2.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab2.domains.Brand;

@Repository("brandRepository")
public interface BrandRepository extends PersistableRepository<Brand, Long> {

  boolean existsByName(final String name);

  Optional<Brand> findByName(final String name);

  Page<Brand> findAllByName(final String name, final Pageable pageable);
}
