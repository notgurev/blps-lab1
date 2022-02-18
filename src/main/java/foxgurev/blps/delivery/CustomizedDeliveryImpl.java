package foxgurev.blps.delivery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomizedDeliveryImpl implements CustomizedDelivery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public LocalDateTime getLastDate() {
        return em.createQuery("select max(date) from Delivery", LocalDateTime.class).getSingleResult();
    }
}
