package se.ifmo.blos.lab2.repositories;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab2.domains.Credit;

@Repository("creditRepository")
public interface CreditRepository extends PersistableRepository<Credit, UUID> {

  Page<Credit> findAllByApplicantId(final Long id, final Pageable pageable);

  Page<Credit> findAllByManagerId(final Long id, final Pageable pageable);
}
