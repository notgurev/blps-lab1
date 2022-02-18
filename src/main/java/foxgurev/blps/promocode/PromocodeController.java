package foxgurev.blps.promocode;

import foxgurev.blps.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromocodeController {
    private final PromocodeService promocodeService;

    @Autowired
    public PromocodeController(PromocodeService promocodeService) {
        this.promocodeService = promocodeService;
    }

    @GetMapping("/promocode/{code}")
    public Promocode getPromocode(@PathVariable String code) {
        return promocodeService.getPromocode(code).orElseThrow( () -> new NotFoundException("The promocode doesn't exist")); // todo error message
    }
}
