package foxgurev.blps.promocode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromocodeService {
    private final PromocodeRepository promocodeRepository;

    @Autowired
    public PromocodeService(PromocodeRepository promocodeRepository) {
        this.promocodeRepository = promocodeRepository;
    }

    public Optional<Promocode> getPromocode(String code) {
        return promocodeRepository.findByCode(code);
    }
}
