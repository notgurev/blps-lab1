package foxgurev.blps.auth;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.blos.lab2.dtos.AuthorizationDto;
import se.ifmo.blos.lab2.services.AuthorizationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping(path = "/login")
    @PreAuthorize("permitAll()")
    @SneakyThrows
    public AuthorizationDto authorize(@RequestBody @Valid AuthorizationDto authorizationDto) {
        return authorizationService.authorize(authorizationDto);
    }
}
