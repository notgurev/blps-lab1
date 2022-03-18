package se.ifmo.blos.lab2.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.blos.lab2.dtos.AuthorizationDto;
import se.ifmo.blos.lab2.exceptions.UnauthorizedException;
import se.ifmo.blos.lab2.services.AuthorizationService;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationController {

  private final AuthorizationService authorizationService;

  @PostMapping(
      path = "/login",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("permitAll()")
  public AuthorizationDto authorize(final @RequestBody @Valid AuthorizationDto authorizationDto)
      throws UnauthorizedException {
    return authorizationService.authorize(authorizationDto);
  }
}
