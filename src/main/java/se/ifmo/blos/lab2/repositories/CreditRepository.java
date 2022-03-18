package se.ifmo.blos.lab2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab2.domains.Credit;

import java.util.UUID;

@Repository("creditRepository")
public interface CreditRepository extends PersistableRepository<Credit, UUID> {

    Page<Credit> findAllByApplicantId(final Long id, final Pageable pageable);

    Page<Credit> findAllByManagerId(final Long id, final Pageable pageable);
}
