package foxgurev.blps.auth;

import foxgurev.blps.auth.dto.AuthorizationDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {
    private final AuthorizationService authorizationService;

    @PostMapping(path = "/login")
    @PreAuthorize("permitAll()")
    @SneakyThrows
    public AuthorizationDto signIn(@RequestBody @Valid AuthorizationDto authorizationDto) {
        return authorizationService.signIn(authorizationDto);
    }

  @PostMapping(path = "/signup")
  @PreAuthorize("permitAll()")
  @SneakyThrows
  public AuthorizationDto signUp(@RequestBody @Valid AuthorizationDto authorizationDto) {
        return authorizationService.signUp(authorizationDto);
  }
}
